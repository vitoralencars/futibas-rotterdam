package platform.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mmk.kmpauth.firebase.apple.AppleButtonUiContainer
import components.button.login.AppleLoginButton
import login.data.response.LoginAuthResponse

@Composable
actual fun AppleLoginButton(onSignInResponse: (LoginAuthResponse) -> Unit) {
    AppleButtonUiContainer(
        onResult = { result ->
            val user = result.getOrNull()
            when {
                user != null && result.isSuccess -> {
                    user.email?.let { email ->
                        onSignInResponse(LoginAuthResponse.Success.Apple(
                            email = email,
                            uid = user.uid,
                        ))
                    } ?: onSignInResponse(LoginAuthResponse.Error(""))
                }
                else -> onSignInResponse(LoginAuthResponse.Error(""))
            }
        },
        linkAccount = false,
        modifier = Modifier.fillMaxWidth(),
    ) {
        AppleLoginButton(
            onClick = { this.onClick() }
        )
    }
}
