package platform.permission

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalPermissionsApi::class)
actual class PermissionManager actual constructor(
    private val callback: PermissionCallback,
) : PermissionHandler {

    @Composable
    actual override fun askPermission(permission: PermissionType) {
        when (permission) {
            PermissionType.CAMERA -> {
                LaunchPermissionRequest(
                    permissionName = Manifest.permission.CAMERA,
                    permissionType = permission,
                )
            }
            PermissionType.GALLERY -> {
                callback.onPermissionStatus(
                    permission, PermissionStatus.GRANTED
                )
            }
            PermissionType.PUSH_NOTIFICATIONS -> {
                if (Build.VERSION.SDK_INT >= 33) {
                    LaunchPermissionRequest(
                        permissionName = Manifest.permission.POST_NOTIFICATIONS,
                        permissionType = permission,
                    )
                }
            }
        }
    }

    @Composable
    actual override fun isPermissionGranted(permission: PermissionType): Boolean =
        when (permission) {
            PermissionType.CAMERA -> {
                getGrantedStatus(Manifest.permission.CAMERA)
            }
            PermissionType.GALLERY -> true
            PermissionType.PUSH_NOTIFICATIONS -> {
                if (Build.VERSION.SDK_INT >= 33) {
                    getGrantedStatus(Manifest.permission.POST_NOTIFICATIONS)
                } else true
            }
        }

    @Composable
    actual override fun launchSettings() {
        val context = LocalContext.current
        Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", context.packageName, null)
        ).also {
            context.startActivity(it)
        }
    }

    @Composable
    private fun LaunchPermissionRequest(
        permissionName: String,
        permissionType: PermissionType,
    ) {
        val permissionState = rememberPermissionState(
            permission = permissionName
        ) { isGranted ->
            callback.onPermissionStatus(
                permissionType = permissionType,
                status = if (isGranted) PermissionStatus.GRANTED else PermissionStatus.DENIED
            )
        }
        LaunchedEffect(permissionState) {
            val permissionResult = permissionState.status
            if (!permissionResult.isGranted) {
                coroutineScope {
                    launch {
                        permissionState.launchPermissionRequest()
                    }
                }
            }
        }
    }

    @Composable
    private fun getGrantedStatus(permission: String): Boolean {
        val permissionState = rememberPermissionState(permission)
        return permissionState.status.isGranted
    }
}

@Composable
actual fun createPermissionManager(callback: PermissionCallback): PermissionManager {
    return remember { PermissionManager(callback) }
}
