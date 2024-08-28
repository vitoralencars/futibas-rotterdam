package common.util.image

import androidx.compose.runtime.Composable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import platform.image.ImageSourceManager
import platform.permission.PermissionCallback
import platform.permission.PermissionStatus
import platform.permission.PermissionType
import platform.permission.createPermissionManager

@Composable
fun ImagePicker(
    permissionType: PermissionType,
    imageSourceManager: ImageSourceManager,
    onPermissionResult: () -> Unit,
) {
    val permissionManager = createPermissionManager(object : PermissionCallback {
        override fun onPermissionStatus(
            permissionType: PermissionType,
            status: PermissionStatus
        ) {
            if (status == PermissionStatus.GRANTED) {
                CoroutineScope(Dispatchers.Main).launch {
                    imageSourceManager.launch()
                }
            }
            onPermissionResult()
        }
    })

    if(!permissionManager.isPermissionGranted(permissionType)) {
        permissionManager.askPermission(permissionType)
    } else {
        imageSourceManager.launch()
        onPermissionResult()
    }
}
