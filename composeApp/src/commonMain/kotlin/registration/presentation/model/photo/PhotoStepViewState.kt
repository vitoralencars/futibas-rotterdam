package registration.presentation.model.photo

import androidx.compose.ui.graphics.ImageBitmap
import platform.image.Image

sealed interface PhotoStepViewState {
    val imageSourceItems: List<ImageSourceItem>

    data class LoadingPhoto(
        override val imageSourceItems: List<ImageSourceItem>,
    ) : PhotoStepViewState

    sealed interface Content : PhotoStepViewState {
        data class Empty(
            override val imageSourceItems: List<ImageSourceItem>,
        ) : Content

        data class Photo(
            override val imageSourceItems: List<ImageSourceItem>,
            val image: Image,
            val photoBitmap: ImageBitmap,
        ) : Content
    }
}
