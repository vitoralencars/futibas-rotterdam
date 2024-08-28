package platform.ui

import androidx.compose.runtime.Composable

@Composable
expect fun AppleLoginButton(
    onSignInResponse: () -> Unit,
)
