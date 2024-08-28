package platform.image

import androidx.compose.runtime.Composable
import platform.Image

@Composable
expect fun rememberGalleryManager(onResult: (Image?) -> Unit): ImageSourceManager
