package registration.presentation.viewmodel

import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.viewModelScope
import common.base.BaseViewModel
import common.model.BrazilianState
import common.model.Country
import components.stepper.Step
import components.stepper.StepStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import login.domain.model.FirebaseAccount
import network.ServiceResult
import platform.image.Image
import player.domain.usecase.StoreLoggedInPlayerUseCase
import registration.domain.model.PlayerDataRegister
import registration.domain.usecase.GetBrazilianStatesUseCase
import registration.domain.usecase.GetCountriesUseCase
import registration.domain.usecase.RegisterPlayerUseCase
import registration.presentation.model.photo.PhotoStepViewState
import registration.presentation.model.RegistrationStep

class RegistrationViewModel(
    firebaseAccount: FirebaseAccount,
    private val getCountries: GetCountriesUseCase,
    private val getBrazilianStates: GetBrazilianStatesUseCase,
    private val registerPlayerUseCase: RegisterPlayerUseCase,
    private val storeLoggedInPlayerUseCase: StoreLoggedInPlayerUseCase,
) : BaseViewModel<RegistrationScreenState, RegistrationSideEffect>(
    initialState = createRegistrationScreenState(
        firebaseAccount = firebaseAccount,
    )
) {

    init {
        setUpLocations()
    }

    fun onNameInputChange(value: String) {
        updateState {
            it.copy(
                identification = it.identification.copy(
                    name = value,
                )
            )
        }
        viewModelScope.launch {
            emitSideEffect(RegistrationSideEffect.NavigateBack)
        }
        checkContinueButton()
    }

    fun onNicknameInputChange(value: String) {
        updateState {
            it.copy(
                identification = it.identification.copy(
                    nickname = value.ifEmpty { null },
                )
            )
        }
        checkContinueButton()
    }

    fun onBirthdayFieldTapped() {
        updateState {
            it.copy(
                identification = it.identification.copy(
                    shouldShowDatePickerDialog = true,
                )
            )
        }
    }

    fun onBirthdayFieldDismissed() {
        updateState {
            it.copy(
                identification = it.identification.copy(
                    shouldShowDatePickerDialog = false,
                )
            )
        }
    }

    fun onBirthdaySelected(value: String?) {
        updateState {
            it.copy(
                identification = it.identification.copy(
                    birthday = value,
                    shouldShowDatePickerDialog = false,
                )
            )
        }
    }

    fun onFavoriteTeamInputChange(value: String) {
        updateState {
            it.copy(
                identification = it.identification.copy(
                    favoriteTeam = value,
                )
            )
        }
        checkContinueButton()
    }

    fun onCountrySelected(value: Country) {
        val shouldShowStateField = value.name == BRAZIL

        updateState {
            it.copy(
                location = it.location.copy(
                    selectedCountry = value,
                    selectedState = null,
                    shouldShowStateField = shouldShowStateField,
                    shouldShowCityField = !shouldShowStateField,
                )
            )
        }
        checkContinueButton()
    }

    fun onBrazilianStateSelected(value: BrazilianState) {
        updateState {
            it.copy(
                location = it.location.copy(
                    selectedState = value,
                    shouldShowCityField = true,
                )
            )
        }
        checkContinueButton()
    }

    fun onCityInputChanged(value: String) {
        updateState {
            it.copy(
                location = it.location.copy(
                    selectedCity = value,
                )
            )
        }
        checkContinueButton()
    }

    fun onPhotoSet(value: Image?) {
        updateState { state ->
            state.copy(photo = PhotoStepViewState.LoadingPhoto(state.photo.imageSourceItems))
        }

        checkContinueButton()

        viewModelScope.launch {
            setSelectedPhoto(photo = value)
            checkContinueButton()
        }
    }

    fun onContinueTapped() {
        val stepIndicator = state.value.currentStep.stepIndex

        when (stepIndicator) {
            0 -> updateState {
                it.copy(
                    currentStep = RegistrationStep.Location(),
                    isBottomSheetExpanded = true,
                )
            }
            1 -> updateState {
                it.copy(
                    currentStep = RegistrationStep.Photo(),
                    isBottomSheetExpanded = false,
                )
            }
            2 -> {
                registerPlayer()
            }
        }

        updateStepper()
        checkContinueButton()
    }

    private fun setUpLocations() {
        viewModelScope.launch {
            val countries = getCountries()
            val brazilianStates = getBrazilianStates()
            updateState { state ->
                state.copy(
                    location = state.location.copy(
                        countriesList = countries,
                        brazilianStatesList = brazilianStates,
                    )
                )
            }
        }
    }

    private suspend fun setSelectedPhoto(photo: Image?) {
        var photoBitmap: ImageBitmap?

        withContext(Dispatchers.Default) {
            photoBitmap = photo?.toImageBitmap()
        }

        updateState { state ->
            val imageSourceItems = state.photo.imageSourceItems

            val photoStepViewState = if (photo != null && photoBitmap != null) {
                PhotoStepViewState.Content.Photo(
                    image = photo,
                    imageSourceItems = imageSourceItems,
                    photoBitmap = photoBitmap!!,
                )
            } else {
                PhotoStepViewState.Content.Empty(
                    imageSourceItems = imageSourceItems,
                )
            }

            state.copy(photo = photoStepViewState)
        }
    }

    private fun registerPlayer() {
        updateState {
            it.copy(
                isRegisteringPlayer = true,
                photo = it.photo,
            )
        }

        val state = state.value

        val photoData = if (state.photo is PhotoStepViewState.Content.Photo) {
            state.photo.image.toBase64()
        } else {
            null
        }

        viewModelScope.launch {
            val result = registerPlayerUseCase(
                playerDataRegister = PlayerDataRegister(
                    playerId = state.firebaseAccount.uid,
                    email = state.firebaseAccount.email,
                    name = state.identification.name,
                    nickname = state.identification.nickname,
                    birthday = state.identification.birthday,
                    favoriteTeam = state.identification.favoriteTeam,
                    city = state.location.selectedCity,
                    state = state.location.selectedState?.name,
                    country = state.location.selectedCountry?.name.orEmpty(),
                    photoData = photoData,
                )
            )

            when (result) {
                is ServiceResult.Success -> {
                    storeLoggedInPlayerUseCase(result.response)
                    emitSideEffect(RegistrationSideEffect.FinishRegistration)
                }
                is ServiceResult.Error -> {
                    updateState {
                        it.copy(
                            isRegisteringPlayer = false,
                        )
                    }
                }
            }
        }
    }

    private fun updateStepper() {
        val currentStep = state.value.currentStep
        val currentStepperViewState = state.value.stepperViewState

        val updatedSteps = currentStepperViewState.steps.mapIndexed { index, step ->
            val titleResId = step.titleResId
            val status = when {
                index == currentStep.stepIndex -> StepStatus.Current
                index < currentStep.stepIndex -> StepStatus.Done
                else -> StepStatus.Future
            }
            Step(
                titleResId = titleResId,
                status = status,
            )
        }

        updateState { state ->
            state.copy(
                stepperViewState = state.stepperViewState.copy(
                    steps = updatedSteps,
                )
            )
        }
    }

    private fun checkContinueButton() {
        val state = state.value
        val isContinueEnabled = when (state.currentStep) {
            is RegistrationStep.Identification -> {
                with(state.identification) {
                    name.isNotEmpty() && favoriteTeam.isNotEmpty()
                }
            }
            is RegistrationStep.Location -> {
                with(state.location) {
                    when {
                        selectedCountry == null -> false
                        shouldShowStateField && selectedState == null -> false
                        shouldShowCityField && selectedCity.isEmpty() -> false
                        else -> true
                    }
                }
            }
            is RegistrationStep.Photo -> {
                when (state.photo) {
                    is PhotoStepViewState.LoadingPhoto -> false
                    else -> true
                }
            }
        }

        updateState {
            state.copy(
                isContinueEnabled = isContinueEnabled
            )
        }
    }

    private companion object {
        const val BRAZIL = "Brasil"
    }
}
