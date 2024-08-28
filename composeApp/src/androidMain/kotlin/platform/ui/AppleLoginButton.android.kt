package platform.ui

import androidx.compose.runtime.Composable
import login.data.response.LoginAuthResponse

@Composable
actual fun AppleLoginButton(onSignInResponse: (LoginAuthResponse) -> Unit) {
}
