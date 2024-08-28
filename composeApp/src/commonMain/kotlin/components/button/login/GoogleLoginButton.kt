package components.button.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import common.ui.theme.dimensions
import common.ui.theme.themeColors
import common.ui.theme.typographies
import futibasrotterdam.composeapp.generated.resources.Res
import futibasrotterdam.composeapp.generated.resources.google_login_button
import futibasrotterdam.composeapp.generated.resources.icon_google
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun GoogleLoginButton(
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(dimensions.gap4),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = themeColors.googleLoginButtonBackground,
        ),
        border = BorderStroke(
            width = dimensions.borderThickness,
            color = themeColors.dividerColor
        ),
        contentPadding = PaddingValues(dimensions.gap3),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Image(
            painter = painterResource(Res.drawable.icon_google),
            contentDescription = null,
            modifier = Modifier.size(dimensions.gap8)
        )
        Spacer(modifier = Modifier.width(dimensions.gap4))
        Text(
            text = stringResource(Res.string.google_login_button),
            style = typographies.h2,
            modifier = Modifier.weight(1f)
        )
    }
}
