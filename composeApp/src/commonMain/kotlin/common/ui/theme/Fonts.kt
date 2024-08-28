package common.ui.theme


import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import futibasrotterdam.composeapp.generated.resources.Res
import futibasrotterdam.composeapp.generated.resources.dobraslab_bold
import futibasrotterdam.composeapp.generated.resources.dobraslab_book
import futibasrotterdam.composeapp.generated.resources.dobraslab_medium
import futibasrotterdam.composeapp.generated.resources.opensans_bold
import futibasrotterdam.composeapp.generated.resources.opensans_regular
import org.jetbrains.compose.resources.Font

@Composable
fun DobrasLabFontFamily() = FontFamily(
    Font(
        resource = Res.font.dobraslab_bold,
        weight = FontWeight.Black,
        style = FontStyle.Normal
    ),
    Font(
        resource = Res.font.dobraslab_medium,
        weight = FontWeight.Bold,
        style = FontStyle.Normal
    ),
    Font(
        resource = Res.font.dobraslab_book,
        weight = FontWeight.Normal,
        style = FontStyle.Normal
    )
)

@Composable
fun OpenSansFontFamily() = FontFamily(
    Font(
        resource = Res.font.opensans_bold,
        weight = FontWeight.Bold,
        style = FontStyle.Normal
    ),
    Font(
        resource = Res.font.opensans_regular,
        weight = FontWeight.Normal,
        style = FontStyle.Normal
    )
)
