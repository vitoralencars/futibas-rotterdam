package registration.ui.step

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import common.ui.theme.dimensions
import common.ui.theme.themeColors
import common.ui.theme.typographies
import common.util.image.ImageSource
import common.util.image.ImagePicker
import components.bottomsheet.BottomSheetManager
import components.image.ImageSourceOptions
import futibasrotterdam.composeapp.generated.resources.Res
import futibasrotterdam.composeapp.generated.resources.add_photo
import futibasrotterdam.composeapp.generated.resources.registration_photo_add
import futibasrotterdam.composeapp.generated.resources.registration_photo_change
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import platform.image.Image
import platform.image.ImageSourceManager
import platform.image.rememberCameraManager
import platform.image.rememberGalleryManager
import registration.presentation.model.photo.PhotoStepViewState

@Composable
fun PhotoStep(
    viewState: PhotoStepViewState,
    bottomSheetManager: BottomSheetManager,
    onPhotoSet: (Image?) -> Unit,
) {
    val coroutineScope: CoroutineScope = rememberCoroutineScope()
    var selectedImageSource by remember { mutableStateOf<ImageSource?>(null) }

    val cameraManager = rememberCameraManager { photo ->
        coroutineScope.launch {
            withContext(Dispatchers.Main) {
                bottomSheetManager.hide()
                onPhotoSet(photo)
            }
        }
    }
    val galleryManager = rememberGalleryManager { photo ->
        coroutineScope.launch {
            withContext(Dispatchers.Main) {
                bottomSheetManager.hide()
                onPhotoSet(photo)
            }
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        when (viewState) {
            is PhotoStepViewState.LoadingPhoto -> {
                CircularProgressIndicator(
                    color = themeColors.mainColor
                )
            }
            is PhotoStepViewState.Content -> {
                PhotoContent(
                    viewState = viewState,
                    bottomSheetManager = bottomSheetManager,
                    cameraManager = cameraManager,
                    galleryManager = galleryManager,
                    onImageSourceSelected = { imageSource ->
                        selectedImageSource = imageSource
                    }
                )
            }
        }
    }

    selectedImageSource?.let {
        ImagePicker(
            permissionType = it.permissionType,
            imageSourceManager = it.imageSourceManager,
            onPermissionResult = {
                selectedImageSource = null
            },
        )
    }
}

@Composable
private fun PhotoContent(
    viewState: PhotoStepViewState.Content,
    bottomSheetManager: BottomSheetManager,
    cameraManager: ImageSourceManager,
    galleryManager: ImageSourceManager,
    onImageSourceSelected: (ImageSource) -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(dimensions.gap4),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(dimensions.gap4)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                bottomSheetManager.show {
                    ImageSourceOptions(
                        imageSourceItems = viewState.imageSourceItems,
                        imageSources = listOf(
                            ImageSource.Camera(imageSourceManager = cameraManager),
                            ImageSource.Gallery(imageSourceManager = galleryManager),
                        ),
                        onImageSourceSelected = { imageSource ->
                            onImageSourceSelected(imageSource)
                        },
                    )
                }
            }
    ) {
        when (viewState) {
            is PhotoStepViewState.Content.Photo -> LoadedPhoto(photo = viewState.photoBitmap)
            is PhotoStepViewState.Content.Empty -> AddPhoto()
        }
    }
}

@Composable
private fun LoadedPhoto(
    photo: ImageBitmap,
) {
    Image(
        bitmap = photo,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier.size(150.dp).clip(CircleShape)
    )

    InstructionText(
        text = stringResource(Res.string.registration_photo_change),
    )
}

@Composable
private fun AddPhoto() {
    Image(
        painter = painterResource(Res.drawable.add_photo),
        contentDescription = null,
        modifier = Modifier.size(150.dp)
    )

    InstructionText(
        text = stringResource(Res.string.registration_photo_add),
    )
}

@Composable
private fun InstructionText(
    text: String,
) {
    Text(
        text = text,
        style = typographies.h2,
        color = themeColors.mainColor,
    )
}
