package platform.image

import androidx.compose.ui.graphics.ImageBitmap

expect class Image {
    fun toByteArray(): ByteArray?
    fun toImageBitmap(): ImageBitmap?
    fun toBase64(): String?
}
