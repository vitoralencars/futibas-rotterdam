package profile.presentation.menu.personaldata

import common.model.BrazilianState
import common.model.Country
import components.button.ButtonViewState
import player.domain.model.LoggedInPlayer

sealed interface PersonalDataScreenState {
    data object Initial: PersonalDataScreenState

    data class Content(
        val loggedInPlayer: LoggedInPlayer,
        val countryFlag: String,
        val countriesList: List<Country>,
        val brazilianStatesList: List<BrazilianState>,
        val selectedBrazilianStateAbbreviation: String?,
        val shouldShowBrazilianStateField: Boolean,
        val shouldShowPickerDialog: Boolean = false,
        val isUploadingData: Boolean = false,
        val canUploadData: Boolean = false,
    ) : PersonalDataScreenState

    fun asContent(): Content? = this as? Content
}
