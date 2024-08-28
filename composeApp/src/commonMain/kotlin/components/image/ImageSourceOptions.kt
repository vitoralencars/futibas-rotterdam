package components.image

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import common.ui.theme.dimensions
import common.ui.theme.themeColors
import common.ui.theme.typographies
import common.util.image.ImageSource
import futibasrotterdam.composeapp.generated.resources.Res
import futibasrotterdam.composeapp.generated.resources.image_source_request_source
import org.jetbrains.compose.resources.stringResource
import registration.presentation.model.photo.ImageSourceItem

@Composable
fun ImageSourceOptions(
    imageSourceItems: List<ImageSourceItem>,
    imageSources: List<ImageSource>,
    onImageSourceSelected: (ImageSource) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensions.gap4)
    ) {
        Text(
            text = stringResource(Res.string.image_source_request_source),
            style = typographies.h2,
        )
        Spacer(Modifier.height(dimensions.gap4))
        imageSourceItems.forEach { item ->
            ImageSource(
                imageSourceItem = item,
                onItemTapped = {
                    val selectedImageSource = imageSources.find { imageSource ->
                        imageSource.permissionType == item.permissionType
                    }
                    selectedImageSource?.let { imageSource ->
                        onImageSourceSelected(imageSource)
                    }
                }
            )
        }
    }
}

@Composable
private fun ImageSource(
    imageSourceItem: ImageSourceItem,
    onItemTapped: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable (
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() },
                ) {
                    onItemTapped()
                }
                .padding(vertical = dimensions.gap4)
        ) {
            Icon(
                imageVector = imageSourceItem.icon,
                contentDescription = null,
                tint = themeColors.imageSourceIconColor,
            )

            Spacer(Modifier.width(dimensions.gap4))

            Text(
                text = stringResource(imageSourceItem.titleResId),
                style = typographies.body,
            )
        }
        Divider(
            color = themeColors.dividerColor,
            thickness = dimensions.dividerThickness,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
