package platform.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.interop.LocalUIViewController
import cocoapods.GoogleSignIn.GIDSignIn
import cocoapods.GoogleSignIn.GIDSignInResult
import components.button.login.GoogleLoginButton
import dev.gitlive.firebase.auth.GoogleAuthProvider
import kotlinx.cinterop.ExperimentalForeignApi
import login.data.GoogleAuthResponse
import platform.UIKit.UIViewController

@Composable
actual fun GoogleLoginButton(
    onSignInResponse: (GoogleAuthResponse) -> Unit,
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
    onSignInResponse: (GoogleAuthResponse) -> Unit
) {
    GIDSignIn.sharedInstance.signInWithPresentingViewController(uiViewController) { result, error ->
        when {
            result != null -> onSignInResponse(result.toGoogleAuthResponse())
            error != null -> onSignInResponse(GoogleAuthResponse.Error(""))
            else -> onSignInResponse(GoogleAuthResponse.Cancelled)
        }
    }
}

@OptIn(ExperimentalForeignApi::class)
private fun GIDSignInResult.toGoogleAuthResponse(): GoogleAuthResponse = try {
    GoogleAuthResponse.Success(GoogleAuthProvider.credential(
        idToken = user.idToken?.tokenString,
        accessToken = user.accessToken.tokenString,
    ))
} catch (e: Exception) {
    GoogleAuthResponse.Error("")
}
