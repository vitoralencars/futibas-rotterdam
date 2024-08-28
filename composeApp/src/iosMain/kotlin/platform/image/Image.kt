package platform.image

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.get
import kotlinx.cinterop.reinterpret
import org.jetbrains.skia.Image
import platform.UIKit.UIImage
import platform.UIKit.UIImageJPEGRepresentation
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

actual class Image(private val image: UIImage?) {

    @OptIn(ExperimentalForeignApi::class)
    actual fun toByteArray(): ByteArray? =
        image?.let {
            val imageData = UIImageJPEGRepresentation(image, COMPRESSION_QUALITY)
                ?: throw IllegalArgumentException("image data is null")
            val bytes = imageData.bytes ?: throw IllegalArgumentException("image bytes is null")
            val length = imageData.length

            val data: CPointer<ByteVar> = bytes.reinterpret()

            ByteArray(length.toInt()) { index ->
                data[index]
            }
        }

    actual fun toImageBitmap(): ImageBitmap? {
        val byteArray = toByteArray()

        return byteArray?.let {
            Image.makeFromEncoded(it).toComposeImageBitmap()
        }
    }

    @OptIn(ExperimentalEncodingApi::class)
    actual fun toBase64(): String? {
        val byteArray = toByteArray()

        return byteArray?.let {
            Base64.encode(it)
        }
    }

    private companion object {
        const val COMPRESSION_QUALITY = 0.5
    }
}
