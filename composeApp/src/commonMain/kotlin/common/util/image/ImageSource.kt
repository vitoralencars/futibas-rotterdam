package common.util.image

import platform.image.ImageSourceManager
import platform.permission.PermissionType

sealed interface ImageSource {
    val imageSourceManager: ImageSourceManager
    val permissionType: PermissionType

    data class Camera(
        override val imageSourceManager: ImageSourceManager,
        override val permissionType: PermissionType = PermissionType.CAMERA,
    ) : ImageSource

    data class Gallery(
        override val imageSourceManager: ImageSourceManager,
        override val permissionType: PermissionType = PermissionType.GALLERY,
    ) : ImageSource
}
