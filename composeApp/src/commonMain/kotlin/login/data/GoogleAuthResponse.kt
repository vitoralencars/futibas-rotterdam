package login.data

import login.domain.model.FirebaseAccount

sealed interface GoogleAuthResponse {
    data class Success(val account: FirebaseAccount) : GoogleAuthResponse
    data class Error(val message: String) : GoogleAuthResponse
    data object Cancelled : GoogleAuthResponse
}
