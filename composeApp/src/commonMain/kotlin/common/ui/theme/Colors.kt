package common.ui.theme

import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

object Colors {
    val futibasGreen = Color(0xFF49A359)
    val white = Color(0xFFFFFFFF)
    val black = Color(0xFF000000)
    val red = Color(0xFFFF0000)
    val lightGrey = Color(0XFF777777)
    val veryLightGrey = Color(0XFFCCCCCC)

    val schemeColors = lightColorScheme(
        primary = futibasGreen,
        secondary = white,
        secondaryContainer = white,
    )
}
