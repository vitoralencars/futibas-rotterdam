package platform

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.credentials.Credential
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.rotterdam.futibas.FutibasApplication
import common.ui.theme.dimensions
import common.ui.theme.themeColors
import common.ui.theme.typographies
import futibasrotterdam.composeapp.generated.resources.Res
import futibasrotterdam.composeapp.generated.resources.google_login_button
import futibasrotterdam.composeapp.generated.resources.icon_google
import kotlinx.coroutines.launch
import login.data.GoogleAuthResponse
import login.domain.model.FirebaseAccount
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
actual fun GoogleLoginButton(
    onResponse: (GoogleAuthResponse) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    GoogleLoginButton(
        onClick = {
            coroutineScope.launch {
                val firebaseAccount = signIn(context)
                firebaseAccount?.let {
                    onResponse(GoogleAuthResponse.Success(
                        it
                    ))
                } ?: run {
                    onResponse(GoogleAuthResponse.Error(""))
                }
            }
        },
    )
}

private suspend fun signIn(activityContext: Context): FirebaseAccount? {
    val credentialManager = CredentialManager.create(FutibasApplication.CONTEXT)
    return try {
        val credential = credentialManager.getCredential(
            context = activityContext,
            request = getCredentialRequest()
        ).credential
        getGoogleUserFromCredential(credential)
    } catch (e: GetCredentialException) {
        null
    } catch (e: NullPointerException) {
        null
    }
}

private fun getGoogleUserFromCredential(credential: Credential): FirebaseAccount? {
    return when {
        credential is CustomCredential &&
                credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL -> {
            try {
                val googleIdTokenCredential =
                    GoogleIdTokenCredential.createFrom(credential.data)

                val googleAuthProviderCredential = GoogleAuthProvider.getCredential(
                    googleIdTokenCredential.idToken,
                    null
                )

                val firebaseInstance = FirebaseAuth.getInstance()
                firebaseInstance.signInWithCredential(googleAuthProviderCredential)

                val firebaseUser = firebaseInstance.currentUser

                if (firebaseUser != null && firebaseUser.email != null) {
                    FirebaseAccount(
                        uid = firebaseUser.uid,
                        email = firebaseUser.email!!,
                    )
                } else {
                    throw Exception()
                }

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
        .addCredentialOption(getGoogleIdOption(
            serverClientId = "703400968272-5a79smd29s9vaigmc5ebmsq51poa9rgd.apps.googleusercontent.com"
        ))
        .build()
}

private fun getGoogleIdOption(serverClientId: String): GetGoogleIdOption {
    return GetGoogleIdOption.Builder()
        .setFilterByAuthorizedAccounts(false)
        .setAutoSelectEnabled(true)
        .setServerClientId(serverClientId)
        .build()
}

@Composable
private fun GoogleLoginButton(
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(dimensions.gap4),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = themeColors.lightBackground,
        ),
        border = BorderStroke(
            width = dimensions.borderThickness,
            color = themeColors.dividerColor
        ),
        contentPadding = PaddingValues(dimensions.gap3),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Image(
            painter = painterResource(Res.drawable.icon_google),
            contentDescription = null,
            modifier = Modifier.size(dimensions.gap8)
        )
        Spacer(modifier = Modifier.width(dimensions.gap4))
        Text(
            text = stringResource(Res.string.google_login_button),
            style = typographies.h2,
            modifier = Modifier.weight(1f)
        )
    }
}
