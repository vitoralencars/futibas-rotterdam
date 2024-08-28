package registration.ui.step.location

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import common.model.BrazilianState
import common.model.Country
import common.ui.theme.dimensions
import components.bottomsheet.BottomSheetManager
import components.form.ui.FormField
import components.form.viewstate.FormFieldViewState
import components.form.ui.LocationSelectionForm
import components.form.viewstate.LocationFormItemViewState
import futibasrotterdam.composeapp.generated.resources.Res
import futibasrotterdam.composeapp.generated.resources.registration_location_city
import futibasrotterdam.composeapp.generated.resources.registration_location_country
import futibasrotterdam.composeapp.generated.resources.registration_location_state
import org.jetbrains.compose.resources.stringResource
import registration.presentation.model.location.LocationStepViewState

@Composable
fun LocationStep(
    viewState: LocationStepViewState,
    bottomSheetManager: BottomSheetManager,
    onCityInputChange: (String) -> Unit,
    onCountrySelected: (Country) -> Unit,
    onBrazilianStateSelected: (BrazilianState) -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(dimensions.gap4),
        modifier = Modifier.fillMaxSize(),
    ) {

        CountryForm(
            viewState = viewState,
            bottomSheetManager = bottomSheetManager,
            onCountrySelected = onCountrySelected,
        )

        if (viewState.shouldShowStateField) {
            BrazilianStateForm(
                viewState = viewState,
                bottomSheetManager = bottomSheetManager,
                onBrazilianStateSelected = onBrazilianStateSelected,
            )
        }

        if (viewState.shouldShowCityField) {
            FormField(
                viewState = FormFieldViewState(
                    label = stringResource(Res.string.registration_location_city),
                    input = viewState.selectedCity
                ),
                onInputChange = onCityInputChange,
                keyboardOptions = KeyboardOptions.Default.copy(
                    capitalization = KeyboardCapitalization.Words,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun CountryForm(
    viewState: LocationStepViewState,
    bottomSheetManager: BottomSheetManager,
    onCountrySelected: (Country) -> Unit,
) {
    LocationSelectionForm(
        formFieldViewState = FormFieldViewState(
            label = stringResource(Res.string.registration_location_country),
            input = "${viewState.selectedCountry?.flag.orEmpty()}\t${viewState.selectedCountry?.name.orEmpty()}",
        ),
        locationFormItemViewStates = viewState.countriesList.map { country ->
            LocationFormItemViewState(
                placeName = country.name,
                flag = country.flag,
            )
        },
        bottomSheetManager = bottomSheetManager,
        onItemSelected = { item ->
            onCountrySelected(Country(
                name = item.placeName,
                flag = item.flag.orEmpty(),
            ))
        }
    )
}

@Composable
private fun BrazilianStateForm(
    viewState: LocationStepViewState,
    bottomSheetManager: BottomSheetManager,
    onBrazilianStateSelected: (BrazilianState) -> Unit,
) {
    LocationSelectionForm(
        formFieldViewState = FormFieldViewState(
            label = stringResource(Res.string.registration_location_state),
            input = viewState.selectedState?.name.orEmpty(),
        ),
        locationFormItemViewStates = viewState.brazilianStatesList.map { brazilianState ->
            LocationFormItemViewState(
                placeName = brazilianState.name,
                abbreviation = brazilianState.abbreviation,
            )
        },
        bottomSheetManager = bottomSheetManager,
        onItemSelected = { item ->
            onBrazilianStateSelected(BrazilianState(
                name = item.placeName,
                abbreviation = item.abbreviation.orEmpty(),
            ))
        }
    )
}
