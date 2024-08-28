package platform

import androidx.compose.runtime.Composable
import login.data.GoogleAuthResponse

@Composable
actual fun GoogleLoginButton(
    onResponse: (GoogleAuthResponse) -> Unit,
) {

}
