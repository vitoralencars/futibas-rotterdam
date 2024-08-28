package registration.presentation.model.location

import common.model.BrazilianState
import common.model.Country

data class LocationStepViewState(
    val selectedCountry: Country? = null,
    val selectedState: BrazilianState? = null,
    val selectedCity: String = "",
    val countriesList: List<Country> = listOf(),
    val brazilianStatesList: List<BrazilianState> = listOf(),
    val shouldShowStateField: Boolean = false,
    val shouldShowCityField: Boolean = false,
)
