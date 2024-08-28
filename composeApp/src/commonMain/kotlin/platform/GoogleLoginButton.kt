package platform

import androidx.compose.runtime.Composable
import login.data.GoogleAuthResponse

@Composable
expect fun GoogleLoginButton(
    onResponse: (GoogleAuthResponse) -> Unit,
)
