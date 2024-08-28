package platform.ui

import androidx.compose.runtime.Composable
import login.data.response.LoginAuthResponse

@Composable
expect fun AppleLoginButton(
    onSignInResponse: (LoginAuthResponse) -> Unit,
)
