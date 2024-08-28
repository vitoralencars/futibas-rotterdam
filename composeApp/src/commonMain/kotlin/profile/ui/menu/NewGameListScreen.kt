package profile.ui.menu

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.navigation.NavController
import common.ui.theme.dimensions
import common.ui.theme.themeColors
import common.ui.theme.typographies
import components.bottomsheet.BottomSheetManager
import components.button.ButtonViewState
import components.button.PrimaryButton
import components.button.NegativeButton
import components.datepicker.CustomDatePickerDialog
import components.datepicker.CustomTimePickerDialog
import components.form.ui.ClickableFormField
import components.form.ui.FormField
import components.form.viewstate.FormFieldViewState
import components.toolbar.Toolbar
import components.toolbar.ToolbarViewState
import futibasrotterdam.composeapp.generated.resources.Res
import futibasrotterdam.composeapp.generated.resources.configure_list_close_list_button
import futibasrotterdam.composeapp.generated.resources.configure_list_create_list_button
import futibasrotterdam.composeapp.generated.resources.configure_list_date
import futibasrotterdam.composeapp.generated.resources.configure_list_location
import futibasrotterdam.composeapp.generated.resources.configure_list_max_players
import futibasrotterdam.composeapp.generated.resources.configure_list_time
import futibasrotterdam.composeapp.generated.resources.configure_list_update_list_button
import futibasrotterdam.composeapp.generated.resources.profile_menu_configure_list
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import platform.util.DateUtils
import profile.domain.model.Court
import profile.presentation.menu.newgamelist.NewGameListScreenState
import profile.presentation.menu.newgamelist.NewGameListSideEffect
import profile.presentation.menu.newgamelist.NewGameListViewModel

@OptIn(KoinExperimentalAPI::class)
@Composable
fun NewGameListScreen(
    navController: NavController,
    bottomSheetManager: BottomSheetManager,
) {
    val viewModel: NewGameListViewModel = koinViewModel<NewGameListViewModel>()

    val state = viewModel.state.collectAsState().value

    LaunchedEffect(viewModel.sideEffect) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is NewGameListSideEffect.CloseScreen -> navController.navigateUp()
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        Toolbar(
            viewState = ToolbarViewState(stringResource(Res.string.profile_menu_configure_list)),
            navController = navController,
            shouldNavigateBack = true,
        )
        when (state) {
            is NewGameListScreenState.Loading -> LoadingView()
            is NewGameListScreenState.Content -> ContentView(
                state = state,
                bottomSheetManager = bottomSheetManager,
                viewModel = viewModel,
            )
        }
    }
}

@Composable
private fun ContentView(
    state: NewGameListScreenState.Content,
    bottomSheetManager: BottomSheetManager,
    viewModel: NewGameListViewModel,
) {
    NewGameListView(
        state = state,
        bottomSheetManager = bottomSheetManager,
        onDateFieldTapped = {
            viewModel.onDatePickerTapped()
        },
        onDatePickerDismissed = {
            viewModel.onDatePickerDismissed()
        },
        onDateSelected = {
            viewModel.onDateSelected(it)
        },
        onTimeFieldTapped = {
            viewModel.onTimeFieldTapped()
        },
        onTimePickerDismissed = {
            viewModel.onTimeFieldDismissed()
        },
        onTimeSelected = {
            viewModel.onTimeSelected(it)
        },
        onCourSelected = {
            viewModel.onCourtSelected(it)
        },
        onMaxPlayersSet = {
            viewModel.onMaxPlayersSet(it)
        },
        onCreateListButtonClick = {
            state.gameId?.let {
                viewModel.updateGameList()
            } ?: run {
                viewModel.createGameList()
            }
        },
        onCloseListButtonClick = {
            viewModel.closeGameList()
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NewGameListView(
    state: NewGameListScreenState.Content,
    bottomSheetManager: BottomSheetManager,
    onDateFieldTapped:() -> Unit,
    onDatePickerDismissed:() -> Unit,
    onDateSelected: (String) -> Unit,
    onTimeFieldTapped: () -> Unit,
    onTimePickerDismissed: () -> Unit,
    onTimeSelected: (String) -> Unit,
    onCourSelected: (String) -> Unit,
    onMaxPlayersSet: (String) -> Unit,
    onCreateListButtonClick: () -> Unit,
    onCloseListButtonClick: () -> Unit,
) {
    val datePickerState = rememberDatePickerState()
    val timePickerState = rememberTimePickerState()

    Column(
        verticalArrangement = Arrangement.spacedBy(dimensions.gap4),
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensions.gap4)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(dimensions.gap4),
            modifier = Modifier.fillMaxWidth().wrapContentHeight()
        ) {
            ClickableFormField(
                viewState = FormFieldViewState(
                    label = stringResource(Res.string.configure_list_date),
                    input = state.date,
                ),
                onClick = { onDateFieldTapped() },
                modifier = Modifier.weight(1f)
            )
            ClickableFormField(
                viewState = FormFieldViewState(
                    label = stringResource(Res.string.configure_list_time),
                    input = state.time,
                ),
                onClick = { onTimeFieldTapped() },
                modifier = Modifier.weight(1f)
            )
        }
        ClickableFormField(
            viewState = FormFieldViewState(
                label = stringResource(Res.string.configure_list_location),
                input = state.location,
            ),
            onClick = {
                bottomSheetManager.show {
                    state.courts.forEach {
                        CourtItem(
                            court = it,
                            onCourtTapped = {
                                bottomSheetManager.hide()
                                onCourSelected(it.name)
                            },
                        )
                    }
                }
            },
        )
        FormField(
            viewState = FormFieldViewState(
                label = stringResource(Res.string.configure_list_max_players),
                input = if (state.maxPlayers >= 0) state.maxPlayers.toString() else "",
            ),
            onInputChange = onMaxPlayersSet,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(dimensions.gap4),
            modifier = Modifier.fillMaxWidth(),
        ) {
            ConfigureListButton(
                isNewList = state.gameId == null,
                isLoading = state.isConfiguringList,
                isEnabled = state.canCreateList,
                onClick = onCreateListButtonClick,
            )
            state.gameId?.let {
                CloseListButton(
                    isLoading = state.isConfiguringList,
                    onClick = onCloseListButtonClick,
                )
            }
        }
    }

    if (state.shouldShowDatePicker) {
        CustomDatePickerDialog(
            datePickerState = datePickerState,
            onDismiss = onDatePickerDismissed,
            onConfirm = {
                datePickerState.selectedDateMillis?.let { selectedDate ->
                    onDateSelected(DateUtils.formatMillisToFullDate(millis = selectedDate))
                }
            }
        )
    }

    if (state.shouldShowTimePicker) {
        CustomTimePickerDialog(
            timePickerState = timePickerState,
            onDismiss = onTimePickerDismissed,
            onConfirm = {
                onTimeSelected("${timePickerState.hour}:${timePickerState.minute}")
            }
        )
    }
}

@Composable
private fun CourtItem(
    court: Court,
    onCourtTapped: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCourtTapped() }
    ) {
        Text(
            text = court.name,
            style = typographies.body,
            modifier = Modifier.fillMaxWidth().padding(dimensions.gap4)
        )
        Divider(
            color = themeColors.dividerColor,
            thickness = dimensions.dividerThickness,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun ConfigureListButton(
    isNewList: Boolean,
    isLoading: Boolean,
    isEnabled: Boolean,
    onClick: () -> Unit,
) {
    val viewState = if (isLoading) {
        ButtonViewState.Loading
    } else {
        val textResource = if (isNewList)
            Res.string.configure_list_create_list_button
        else
            Res.string.configure_list_update_list_button

        ButtonViewState.Content(
            text = stringResource(textResource),
            isEnabled = isEnabled,
        )
    }

    PrimaryButton(
        viewState = viewState,
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
    )
}

@Composable
private fun CloseListButton(
    isLoading: Boolean,
    onClick: () -> Unit,
) {
    val viewState = if (isLoading) {
        ButtonViewState.Loading
    } else {
        ButtonViewState.Content(
            text = stringResource(Res.string.configure_list_close_list_button),
        )
    }
    NegativeButton(
        viewState = viewState,
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
    )
}

@Composable
private fun LoadingView() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize(),
    ) {
        CircularProgressIndicator(
            color = themeColors.mainColor,
        )
    }
}
