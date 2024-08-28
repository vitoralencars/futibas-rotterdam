package profile.presentation.general

sealed interface ProfileGeneralSideEffect {
    data object NavigateToLogin : ProfileGeneralSideEffect
}
