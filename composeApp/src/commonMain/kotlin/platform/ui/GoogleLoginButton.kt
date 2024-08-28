package platform.ui

import androidx.compose.runtime.Composable
import login.data.GoogleAuthResponse

@Composable
expect fun GoogleLoginButton(
    onSignInResponse: (GoogleAuthResponse) -> Unit,
)
