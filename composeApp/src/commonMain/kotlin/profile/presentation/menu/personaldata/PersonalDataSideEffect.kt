package profile.presentation.menu.personaldata

sealed interface PersonalDataSideEffect {
    data object CloseScreen : PersonalDataSideEffect
}
