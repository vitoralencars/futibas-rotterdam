package registration.presentation.model.photo

import androidx.compose.ui.graphics.vector.ImageVector
import platform.permission.PermissionType

data class ImageSourceItem(
    val icon: ImageVector,
    val title: String,
    val permissionType: PermissionType,
)
