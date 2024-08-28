package platform.image

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.util.Base64
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import java.io.ByteArrayOutputStream

actual class Image(
    private val bitmap: Bitmap?,
) {
    actual fun toByteArray(): ByteArray? =
        bitmap?.let {
            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(
                Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream
            )
            byteArrayOutputStream.toByteArray()
        }

    actual fun toImageBitmap(): ImageBitmap? {
        val byteArray = toByteArray()

        return byteArray?.let {
            getVerticalBitmap(it).asImageBitmap()
        }
    }

    actual fun toBase64(): String? {
        return toByteArray()?.let { originalByteArray ->
            val vertical = Image(bitmap = getVerticalBitmap(originalByteArray)).toByteArray()

            vertical?.let { verticalByteArray ->
                Base64.encodeToString(verticalByteArray, Base64.DEFAULT).replace("\n", "")
            }
        }
    }

    private fun getVerticalBitmap(byteArray: ByteArray): Bitmap {
        val result = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)

        return if (result.width <= result.height) {
            result
        } else {
            val matrix = Matrix().apply {
                postRotate(-90F)
            }

            val rotatedBitmap = Bitmap.createBitmap(
                result,
                0,
                0,
                result.width,
                result.height,
                matrix,
                true
            )

            rotatedBitmap
        }
    }
}
