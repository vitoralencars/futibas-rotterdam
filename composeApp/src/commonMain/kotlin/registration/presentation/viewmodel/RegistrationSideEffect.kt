package registration.presentation.viewmodel

sealed interface RegistrationSideEffect {

    data object NavigateBack: RegistrationSideEffect
    data object FinishRegistration: RegistrationSideEffect
}
