package login.presentation

sealed interface LoginScreenState {

    data object Loading : LoginScreenState

    data object Content : LoginScreenState
}
