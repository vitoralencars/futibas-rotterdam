package registration.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import common.navigation.GlobalNavController
import common.navigation.ScreenNavigation
import common.ui.theme.dimensions
import common.ui.theme.themeColors
import components.bottomsheet.BottomSheetDragHandler
import components.bottomsheet.BottomSheetManager
import components.bottomsheet.rememberBottomSheetManager
import components.button.ButtonViewState
import components.button.PrimaryButton
import components.stepper.Stepper
import futibasrotterdam.composeapp.generated.resources.Res
import futibasrotterdam.composeapp.generated.resources.confirm_button
import login.domain.model.FirebaseAccount
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.parameter.parametersOf
import registration.presentation.viewmodel.RegistrationScreenState
import registration.presentation.viewmodel.RegistrationViewModel
import registration.presentation.model.RegistrationStep
import registration.presentation.viewmodel.RegistrationSideEffect
import registration.ui.step.IdentificationStep
import registration.ui.step.PhotoStep
import registration.ui.step.location.LocationStep

@OptIn(KoinExperimentalAPI::class)
@Composable
fun RegistrationScreen(firebaseAccount: FirebaseAccount) {
    val viewModel = koinViewModel<RegistrationViewModel>(
        parameters = { parametersOf(firebaseAccount) }
    )

    val screenState = viewModel.state.collectAsState().value

    LaunchedEffect(viewModel.sideEffect) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is RegistrationSideEffect.NavigateBack -> {

                }
                is RegistrationSideEffect.FinishRegistration -> {
                    GlobalNavController.navController.navigate(ScreenNavigation.Main.route) {
                        popUpTo(ScreenNavigation.Login.route) {
                            inclusive = true
                        }
                    }
                }
            }
        }
    }

    val bottomSheetManager = rememberBottomSheetManager(
        showExpanded = screenState.isBottomSheetExpanded
    )
    val bottomSheetContent by bottomSheetManager.contentState

    ModalBottomSheetLayout(
        sheetState = bottomSheetManager.sheetState,
        sheetShape = RoundedCornerShape(
            topStart = dimensions.bottomSheetRoundedCorner,
            topEnd = dimensions.bottomSheetRoundedCorner
        ),
        sheetContent = {
            bottomSheetContent?.let { content ->
                BottomSheetDragHandler()
                content()
            }
        }
    ) {
        RegistrationContent(
            screenState = screenState,
            viewModel = viewModel,
            bottomSheetManager = bottomSheetManager,
        )
    }
}

@Composable
private fun RegistrationContent(
    screenState: RegistrationScreenState,
    viewModel: RegistrationViewModel,
    bottomSheetManager: BottomSheetManager,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = themeColors.mainColor)
            .padding(top = dimensions.gap10)
            .clip(shape = RoundedCornerShape(
                topStart = dimensions.gap8,
                topEnd = dimensions.gap8,
            ))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(color = themeColors.secondaryColor)
                .padding(dimensions.gap4)
        ) {
            Stepper(
                viewState = screenState.stepperViewState,
            )
            Spacer(modifier = Modifier.height(dimensions.gap8))
            Box(modifier = Modifier.weight(1f)) {
                StepView(
                    state = screenState,
                    viewModel = viewModel,
                    bottomSheetManager = bottomSheetManager,
                )
            }
            ContinueButton(
                screenState = screenState,
                onContinueTapped = {
                    viewModel.onContinueTapped()
                }
            )
        }
    }
}

@Composable
private fun StepView(
    state: RegistrationScreenState,
    viewModel: RegistrationViewModel,
    bottomSheetManager: BottomSheetManager,
) {
    when (state.currentStep) {
        is RegistrationStep.Identification -> IdentificationStep(
            viewState = state.identification,
            onNameInputChange = { name ->
                viewModel.onNameInputChange(name)
            },
            onNicknameInputChange = { nickname ->
                viewModel.onNicknameInputChange(nickname)
            },
            onBirthdaySelected = { birthday ->
                viewModel.onBirthdaySelected(birthday)
            },
            onBirthdayFieldTapped = {
                viewModel.onBirthdayFieldTapped()
            },
            onBirthdayDatePickerDismissed = {
                viewModel.onBirthdayFieldDismissed()
            },
            onFavoriteTeamInputChange = { team ->
                viewModel.onFavoriteTeamInputChange(team)
            }
        )
        is RegistrationStep.Location -> LocationStep(
            viewState = state.location,
            bottomSheetManager = bottomSheetManager,
            onCountrySelected = { country ->
                viewModel.onCountrySelected(country)
            },
            onBrazilianStateSelected = { brazilianState ->
                viewModel.onBrazilianStateSelected(brazilianState)
            },
            onCityInputChange = { city ->
                viewModel.onCityInputChanged(city)
            },
        )
        is RegistrationStep.Photo -> PhotoStep(
            viewState = state.photo,
            bottomSheetManager = bottomSheetManager,
            onPhotoSet = { photo ->
                viewModel.onPhotoSet(photo)
            },
        )
    }
}

@Composable
private fun ContinueButton(
    screenState: RegistrationScreenState,
    onContinueTapped: () -> Unit,
) {
    val buttonViewState = if (screenState.isRegisteringPlayer) {
        ButtonViewState.Loading
    } else {
        ButtonViewState.Content(
            text = stringResource(Res.string.confirm_button),
            isEnabled = screenState.isContinueEnabled,
        )
    }
    PrimaryButton(
        viewState = buttonViewState,
        onClick = { onContinueTapped() },
        modifier = Modifier.fillMaxWidth(),
    )
}

