@file:OptIn(ExperimentalMaterial3Api::class)

package profile.ui.menu

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.navigation.NavController
import common.model.BrazilianState
import common.model.Country
import common.ui.theme.dimensions
import components.bottomsheet.BottomSheetManager
import components.button.ButtonViewState
import components.button.MainButton
import components.datepicker.CustomDatePickerDialog
import components.form.ui.ClickableFormField
import components.form.ui.FormField
import components.form.viewstate.FormFieldViewState
import components.form.ui.LocationSelectionForm
import components.toolbar.Toolbar
import components.toolbar.ToolbarViewState
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import platform.DateUtils
import profile.presentation.menu.personaldata.PersonalDataScreenState
import profile.presentation.menu.personaldata.PersonalDataViewModel
import components.form.viewstate.LocationFormItemViewState
import profile.presentation.menu.personaldata.PersonalDataSideEffect

@OptIn(KoinExperimentalAPI::class)
@Composable
fun PersonalDataScreen(
    navController: NavController,
    bottomSheetManager: BottomSheetManager,
) {
    val viewModel: PersonalDataViewModel = koinViewModel<PersonalDataViewModel>()

    val state = viewModel.state.collectAsState().value

    LaunchedEffect(viewModel.sideEffect) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is PersonalDataSideEffect.CloseScreen -> navController.navigateUp()
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        Toolbar(
            viewState = ToolbarViewState(title = "Informações pessoais"),
            navController = navController,
            shouldNavigateBack = true,
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(dimensions.gap4),
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensions.gap4)
        ) {
            when (state) {
                is PersonalDataScreenState.Initial -> {}
                is PersonalDataScreenState.Content -> {
                    PersonalDataView(
                        content = state,
                        bottomSheetManager = bottomSheetManager,
                        onNameInputChange = {
                            viewModel.onNameInputChange(it)
                        },
                        onNicknameInputChange = {
                            viewModel.onNicknameInputChange(it)
                        },
                        onBirthdayFieldTapped = {
                            viewModel.onBirthdayFieldTapped()
                        },
                        onBirthdaySelected = {
                            viewModel.onBirthdaySelected(it)
                        },
                        onDatePickerDismissed = {
                            viewModel.onDatePickerDismissed()
                        },
                        onFavoriteTeamInputChange = {
                            viewModel.onFavoriteTeamInputChange(it)
                        },
                        onCountrySelected = {
                            viewModel.onCountrySelected(it)
                        },
                        onBrazilianStateSelected = {
                            viewModel.onBrazilianStateSelected(it)
                        },
                        onCityInputChange = {
                            viewModel.onCityInputChange(it)
                        },
                        modifier = Modifier.weight(1f)
                    )
                    UploadButton(
                        state = state,
                        onButtonTapped = { viewModel.onUploadButtonTapped() },
                    )
                }
            }
        }
    }
}

@Composable
private fun PersonalDataView(
    content: PersonalDataScreenState.Content,
    bottomSheetManager: BottomSheetManager,
    onNameInputChange: (String) -> Unit,
    onNicknameInputChange: (String) -> Unit,
    onBirthdayFieldTapped: () -> Unit,
    onBirthdaySelected: (String) -> Unit,
    onDatePickerDismissed: () -> Unit,
    onFavoriteTeamInputChange: (String) -> Unit,
    onCountrySelected: (Country) -> Unit,
    onBrazilianStateSelected: (BrazilianState) -> Unit,
    onCityInputChange: (String) -> Unit,
    modifier: Modifier,
) {
    val datePickerState = rememberDatePickerState()

    Column(
        verticalArrangement = Arrangement.spacedBy(dimensions.gap4),
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        FormField(
            viewState = FormFieldViewState(
                label = "Nome",
                input = content.loggedInPlayer.name
            ),
            onInputChange = onNameInputChange,
            keyboardOptions = KeyboardOptions.Default.copy(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier.fillMaxWidth()
        )

        FormField(
            viewState = FormFieldViewState(
                label = "Apelido",
                input = content.loggedInPlayer.nickname ?: "",
                isOptional = true,
            ),
            onInputChange = onNicknameInputChange,
            keyboardOptions = KeyboardOptions.Default.copy(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Done,
            ),
            modifier = Modifier.fillMaxWidth()
        )

        ClickableFormField(
            viewState = FormFieldViewState(
                label = "Data de nascimento",
                input = content.loggedInPlayer.birthday,
            ),
            onClick = { onBirthdayFieldTapped() },
        )

        FormField(
            viewState = FormFieldViewState(
                label = "Time do coração",
                input = content.loggedInPlayer.favoriteTeam,
            ),
            onInputChange = onFavoriteTeamInputChange,
            keyboardOptions = KeyboardOptions.Default.copy(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Done,
            ),
            modifier = Modifier.fillMaxWidth()
        )

        CountryForm(
            state = content,
            bottomSheetManager = bottomSheetManager,
            onCountrySelected = onCountrySelected,
        )

        if (content.shouldShowBrazilianStateField) {
            BrazilianStateForm(
                state = content,
                bottomSheetManager = bottomSheetManager,
                onBrazilianStateSelected = onBrazilianStateSelected
            )
        }

        FormField(
            viewState = FormFieldViewState(
                label = "Cidade",
                input = content.loggedInPlayer.city
            ),
            onInputChange = onCityInputChange,
            keyboardOptions = KeyboardOptions.Default.copy(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }

    if (content.shouldShowPickerDialog) {
        CustomDatePickerDialog(
            datePickerState = datePickerState,
            onDismiss = {
                onDatePickerDismissed()
            },
            onConfirm = {
                datePickerState.selectedDateMillis?.let { selectedDate ->
                    onBirthdaySelected(DateUtils.formatMillisToFullDate(millis = selectedDate))
                }
            }
        )
    }
}

@Composable
private fun CountryForm(
    state: PersonalDataScreenState.Content,
    bottomSheetManager: BottomSheetManager,
    onCountrySelected: (Country) -> Unit,
) {
    LocationSelectionForm(
        formFieldViewState = FormFieldViewState(
            label = "País",
            input = "${state.countryFlag}\t${state.loggedInPlayer.country}",
        ),
        locationFormItemViewStates = state.countriesList.map { country ->
            LocationFormItemViewState(
                placeName = country.name,
                flag = country.flag,
            )
        },
        bottomSheetManager = bottomSheetManager,
        onItemSelected = { item ->
            onCountrySelected(
                Country(
                    name = item.placeName,
                    flag = item.flag.orEmpty(),
                )
            )
        }
    )
}

@Composable
private fun BrazilianStateForm(
    state: PersonalDataScreenState.Content,
    bottomSheetManager: BottomSheetManager,
    onBrazilianStateSelected: (BrazilianState) -> Unit,
) {
    LocationSelectionForm(
        formFieldViewState = FormFieldViewState(
            label = "Estado",
            input = state.loggedInPlayer.state.orEmpty(),
        ),
        locationFormItemViewStates = state.brazilianStatesList.map { brazilianState ->
            LocationFormItemViewState(
                placeName = brazilianState.name,
                abbreviation = brazilianState.abbreviation,
            )
        },
        bottomSheetManager = bottomSheetManager,
        onItemSelected = { item ->
            onBrazilianStateSelected(
                BrazilianState(
                    name = item.placeName,
                    abbreviation = item.abbreviation.orEmpty(),
                )
            )
        }
    )
}

@Composable
private fun UploadButton(
    state: PersonalDataScreenState.Content,
    onButtonTapped: () -> Unit,
) {
    val viewState = if (state.isUploadingData) {
        ButtonViewState.Loading
    } else {
        ButtonViewState.Content(
            text = "Salvar alterações",
            isEnabled = state.canUploadData,
        )
    }

    MainButton(
        viewState = viewState,
        onClick = { onButtonTapped() },
        modifier = Modifier.fillMaxWidth(),
    )
}
