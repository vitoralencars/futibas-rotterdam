package login.data

import dev.gitlive.firebase.auth.AuthCredential

sealed interface GoogleAuthResponse {
    data class Success(val authCredential: AuthCredential) : GoogleAuthResponse
    data class Error(val message: String) : GoogleAuthResponse
    data object Cancelled : GoogleAuthResponse
}
