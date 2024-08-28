package platform.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.interop.LocalUIViewController
import cocoapods.GoogleSignIn.GIDSignIn
import cocoapods.GoogleSignIn.GIDSignInResult
import components.button.login.GoogleLoginButton
import dev.gitlive.firebase.auth.GoogleAuthProvider
import kotlinx.cinterop.ExperimentalForeignApi
import login.data.response.LoginAuthResponse
import platform.UIKit.UIViewController

@Composable
actual fun GoogleLoginButton(
    onSignInResponse: (LoginAuthResponse) -> Unit,
) {
    val uiViewController = LocalUIViewController.current

    GoogleLoginButton(
        onClick = {
            signIn(
                uiViewController = uiViewController,
                onSignInResponse = onSignInResponse,
            )
        },
    )
}

@OptIn(ExperimentalForeignApi::class)
private fun signIn(
    uiViewController: UIViewController,
    onSignInResponse: (LoginAuthResponse) -> Unit
) {
    GIDSignIn.sharedInstance.signInWithPresentingViewController(uiViewController) { result, error ->
        when {
            result != null -> onSignInResponse(result.toLoginAuthResponse())
            else -> onSignInResponse(LoginAuthResponse.Error(""))
        }
    }
}

@OptIn(ExperimentalForeignApi::class)
private fun GIDSignInResult.toLoginAuthResponse(): LoginAuthResponse = try {
    LoginAuthResponse.Success.Google(GoogleAuthProvider.credential(
        idToken = user.idToken?.tokenString,
        accessToken = user.accessToken.tokenString,
    ))
} catch (e: Exception) {
    LoginAuthResponse.Error("")
}
