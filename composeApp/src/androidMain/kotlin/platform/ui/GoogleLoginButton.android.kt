package platform.ui

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.credentials.Credential
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import components.button.login.GoogleLoginButton
import dev.gitlive.firebase.auth.AuthCredential
import dev.gitlive.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.launch
import login.data.response.LoginAuthResponse

@Composable
actual fun GoogleLoginButton(
    onSignInResponse: (LoginAuthResponse) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    GoogleLoginButton(
        onClick = {
            coroutineScope.launch {
                signIn(context)?.let { authCredential ->
                    onSignInResponse(LoginAuthResponse.Success.Google(authCredential = authCredential))
                } ?: run {
                    onSignInResponse(LoginAuthResponse.Error(""))
                }
            }
        },
    )
}

private suspend fun signIn(context: Context): AuthCredential? {
    val credentialManager = CredentialManager.create(context)
    return try {
        val credential = credentialManager.getCredential(
            context = context,
            request = getCredentialRequest()
        ).credential
        getGoogleUserFromCredential(credential)
    } catch (e: GetCredentialException) {
        null
    } catch (e: NullPointerException) {
        null
    }
}

private fun getGoogleUserFromCredential(credential: Credential): AuthCredential? {
    return when {
        credential is CustomCredential &&
                credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL -> {
            try {
                val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)

                GoogleAuthProvider.credential(
                    idToken = googleIdTokenCredential.idToken,
                    accessToken = null,
                )
            } catch (e: GoogleIdTokenParsingException) {
                null
            }
            catch (e: Exception) {
                null
            }
        }

        else -> null
    }
}

private fun getCredentialRequest(): GetCredentialRequest {
    return GetCredentialRequest.Builder()
        .addCredentialOption(getGoogleIdOption())
        .build()
}

private fun getGoogleIdOption(): GetGoogleIdOption {
    return GetGoogleIdOption.Builder()
        .setFilterByAuthorizedAccounts(false)
        .setAutoSelectEnabled(true)
        .setServerClientId("703400968272-5a79smd29s9vaigmc5ebmsq51poa9rgd.apps.googleusercontent.com")
        .build()
}
