package registration.presentation.model.photo

import androidx.compose.ui.graphics.vector.ImageVector
import org.jetbrains.compose.resources.StringResource
import platform.permission.PermissionType

data class ImageSourceItem(
    val icon: ImageVector,
    val titleResId: StringResource,
    val permissionType: PermissionType,
)
