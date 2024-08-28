package platform.permission

import android.Manifest
import android.content.Intent
import android.net.Uri
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
    override fun askPermission(permission: PermissionType) {
        when (permission) {
            PermissionType.CAMERA -> {
                val cameraPermissionState = rememberPermissionState(
                    permission = Manifest.permission.CAMERA
                ) { isGranted ->
                    callback.onPermissionStatus(
                        permissionType = permission,
                        status = if (isGranted) PermissionStatus.GRANTED else PermissionStatus.DENIED
                    )
                }
                LaunchedEffect(cameraPermissionState) {
                    val permissionResult = cameraPermissionState.status
                    if (!permissionResult.isGranted) {
                        coroutineScope {
                            launch {
                                cameraPermissionState.launchPermissionRequest()
                            }
                        }
                    }
                }
            }

            PermissionType.GALLERY -> {
                callback.onPermissionStatus(
                    permission, PermissionStatus.GRANTED
                )
            }
        }
    }

    @Composable
    override fun isPermissionGranted(permission: PermissionType): Boolean =
        when (permission) {
            PermissionType.CAMERA -> {
                val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)
                cameraPermissionState.status.isGranted
            }

            PermissionType.GALLERY -> {
                true
            }
        }

    @Composable
    override fun launchSettings() {
        val context = LocalContext.current
        Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", context.packageName, null)
        ).also {
            context.startActivity(it)
        }
    }
}

@Composable
actual fun createPermissionManager(callback: PermissionCallback): PermissionManager {
    return remember { PermissionManager(callback) }
}
