package registration.presentation.model

data class IdentificationStepViewState(
    val name: String = "",
    val nickname: String? = null,
    val birthday: String = "",
    val favoriteTeam: String = "",
    val shouldShowDatePickerDialog: Boolean = false,
)
