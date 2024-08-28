package login.data.response

import dev.gitlive.firebase.auth.AuthCredential

sealed interface LoginAuthResponse {

    sealed interface Success : LoginAuthResponse {
        data class Google(val authCredential: AuthCredential) : Success
        data class Apple(
            val uid: String,
            val name : String,
            val email: String,
        ) : Success
    }

    data class Error(val message: String) : LoginAuthResponse
}
