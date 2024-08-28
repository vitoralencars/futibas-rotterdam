package registration.presentation.viewmodel

import components.stepper.StepperViewState
import login.domain.model.FirebaseAccount
import registration.presentation.model.IdentificationStepViewState
import registration.presentation.model.photo.PhotoStepViewState
import registration.presentation.model.location.LocationStepViewState
import registration.presentation.model.RegistrationStep

data class RegistrationScreenState(
    val firebaseAccount: FirebaseAccount,
    val stepperViewState: StepperViewState,
    val identification: IdentificationStepViewState,
    val location: LocationStepViewState,
    val photo: PhotoStepViewState,
    val currentStep: RegistrationStep = RegistrationStep.Identification(),
    val isContinueEnabled: Boolean = false,
    val isBottomSheetExpanded: Boolean = false,
    val isRegisteringPlayer: Boolean = false,
)
