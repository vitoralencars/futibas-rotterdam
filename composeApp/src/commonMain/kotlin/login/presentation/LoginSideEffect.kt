package login.presentation

interface LoginSideEffect {

    data class NavigateToRegistration(
        val playerId: String,
        val name: String,
        val email: String,
    ): LoginSideEffect

    data object NavigateToMain: LoginSideEffect
}
