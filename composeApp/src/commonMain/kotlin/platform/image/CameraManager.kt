package platform.image

import androidx.compose.runtime.Composable
import platform.image.Image

@Composable
expect fun rememberCameraManager(onResult: (Image?) -> Unit): ImageSourceManager
