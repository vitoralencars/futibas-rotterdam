package registration.presentation.viewmodel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PhotoCamera
import androidx.compose.material.icons.outlined.PhotoLibrary
import components.stepper.Step
import components.stepper.StepStatus
import components.stepper.StepperViewState
import futibasrotterdam.composeapp.generated.resources.Res
import futibasrotterdam.composeapp.generated.resources.image_source_request_camera
import futibasrotterdam.composeapp.generated.resources.image_source_request_gallery
import futibasrotterdam.composeapp.generated.resources.registration_step_identification
import futibasrotterdam.composeapp.generated.resources.registration_step_location
import futibasrotterdam.composeapp.generated.resources.registration_step_photo
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
                    titleResId = Res.string.registration_step_identification,
                    status = StepStatus.Current
                ),
                Step(
                    titleResId = Res.string.registration_step_location,
                    status = StepStatus.Future
                ),
                Step(
                    titleResId = Res.string.registration_step_photo,
                    status = StepStatus.Future
                ),
            )
        ),
        identification = IdentificationStepViewState(
            name = firebaseAccount.name,
        ),
        location = LocationStepViewState(),
        photo = PhotoStepViewState.Content.Empty(
            imageSourceItems = listOf(
                ImageSourceItem(
                    icon = Icons.Outlined.PhotoCamera,
                    titleResId = Res.string.image_source_request_camera,
                    permissionType = PermissionType.CAMERA,
                ),
                ImageSourceItem(
                    icon = Icons.Outlined.PhotoLibrary,
                    titleResId = Res.string.image_source_request_gallery,
                    permissionType = PermissionType.GALLERY,
                ),
            )
        ),
    )
