package registration.presentation.viewmodel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PhotoCamera
import androidx.compose.material.icons.outlined.PhotoLibrary
import components.stepper.Step
import components.stepper.StepStatus
import components.stepper.StepperViewState
import login.domain.model.FirebaseAccount
import platform.permission.PermissionType
import registration.presentation.model.IdentificationStepViewState
import registration.presentation.model.location.LocationStepViewState
import registration.presentation.model.photo.ImageSourceItem
import registration.presentation.model.photo.PhotoStepViewState

internal fun createRegistrationScreenState(
    firebaseAccount: FirebaseAccount,
): RegistrationScreenState =
    RegistrationScreenState(
        firebaseAccount = firebaseAccount,
        stepperViewState = StepperViewState(
            steps = listOf(
                Step(
                    title = "Identificação",
                    status = StepStatus.Current
                ),
                Step(
                    title = "Origem",
                    status = StepStatus.Future
                ),
                Step(
                    title = "Foto",
                    status = StepStatus.Future
                ),
            )
        ),
        identification = IdentificationStepViewState(),
        location = LocationStepViewState(),
        photo = PhotoStepViewState.Content.Empty(
            imageSourceItems = listOf(
                ImageSourceItem(
                    icon = Icons.Outlined.PhotoCamera,
                    title = "Câmera",
                    permissionType = PermissionType.CAMERA,
                ),
                ImageSourceItem(
                    icon = Icons.Outlined.PhotoLibrary,
                    title = "Galeria",
                    permissionType = PermissionType.GALLERY,
                ),
            )
        ),
    )
