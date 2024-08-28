package profile.presentation.menu.personaldata

import androidx.lifecycle.viewModelScope
import common.base.BaseViewModel
import common.model.BrazilianState
import common.model.Country
import kotlinx.coroutines.launch
import network.ServiceResult
import player.domain.usecase.RetrieveLoggedInPlayerUseCase
import player.domain.usecase.StoreLoggedInPlayerUseCase
import profile.domain.model.playerdata.UpdatePlayerDataRequest
import profile.domain.model.playerdata.UpdatedPlayerData
import profile.domain.usecase.UpdatePlayerDataUseCase
import registration.domain.usecase.GetBrazilianStatesUseCase
import registration.domain.usecase.GetCountriesUseCase

class PersonalDataViewModel(
    private val retrieveLoggedInPlayer: RetrieveLoggedInPlayerUseCase,
    private val storeLoggedInPlayer: StoreLoggedInPlayerUseCase,
    private val getCountries: GetCountriesUseCase,
    private val getBrazilianStates: GetBrazilianStatesUseCase,
    private val updatePlayerData: UpdatePlayerDataUseCase,
): BaseViewModel<PersonalDataScreenState, PersonalDataSideEffect>(
    initialState = PersonalDataScreenState.Initial
) {

    init {
        viewModelScope.launch {
            retrievePersonalData()
        }
    }

    private suspend fun retrievePersonalData() {
        val player = retrieveLoggedInPlayer()
        player?.let { loggedInPlayer ->
            val countriesList = getCountries()
            val brazilianStatesList = getBrazilianStates()

            updateState {
                PersonalDataScreenState.Content(
                    loggedInPlayer = loggedInPlayer,
                    countryFlag = getCountryFlag(
                        countryName = loggedInPlayer.country,
                        countriesList = countriesList,
                    ).orEmpty(),
                    countriesList = countriesList,
                    brazilianStatesList = brazilianStatesList,
                    selectedBrazilianStateAbbreviation = getBrazilianStateAbbreviation(
                        stateName = loggedInPlayer.state,
                        brazilianStatesList = brazilianStatesList,
                    ),
                    shouldShowBrazilianStateField = loggedInPlayer.country == BRAZIL,
                )
            }
        }
    }

    private fun getCountryFlag(
        countryName: String,
        countriesList: List<Country>,
    ): String? =
        countriesList.firstOrNull {
            it.name == countryName
        }?.flag

    private fun getBrazilianStateAbbreviation(
        stateName: String?,
        brazilianStatesList: List<BrazilianState>,
    ): String? = brazilianStatesList.firstOrNull {
        it.name == stateName
    }?.abbreviation

    fun onNameInputChange(value: String) {
        state.value.asContent()?.let { state ->
            updateState {
                state.copy(
                    loggedInPlayer = state.loggedInPlayer.copy(
                        name = value,
                    )
                )
            }
            validateData()
        }
    }

    fun onNicknameInputChange(value: String) {
        state.value.asContent()?.let { state ->
            updateState {
                state.copy(
                    loggedInPlayer = state.loggedInPlayer.copy(
                        nickname = value.ifEmpty { null },
                    )
                )
            }
            validateData()
        }
    }

    fun onBirthdayFieldTapped() {
        state.value.asContent()?.let { state ->
            updateState {
                state.copy(
                    shouldShowPickerDialog = true,
                )
            }
        }
    }

    fun onBirthdaySelected(value: String?) {
        state.value.asContent()?.let { state ->
            updateState {
                state.copy(
                    loggedInPlayer = state.loggedInPlayer.copy(
                        birthday = value,
                    ),
                    shouldShowPickerDialog = false,
                )
            }
            validateData()
        }
    }

    fun onDatePickerDismissed() {
        state.value.asContent()?.let { state ->
            updateState {
                state.copy(
                    shouldShowPickerDialog = false,
                )
            }
        }
    }

    fun onFavoriteTeamInputChange(value: String) {
        state.value.asContent()?.let { state ->
            updateState {
                state.copy(
                    loggedInPlayer = state.loggedInPlayer.copy(
                        favoriteTeam = value,
                    )
                )
            }
            validateData()
        }
    }

    fun onCountrySelected(value: Country) {
        state.value.asContent()?.let { state ->
            val shouldShowBrazilianStateField = value.name == BRAZIL
            val selectedStateName = state.loggedInPlayer.state

            updateState {
                state.copy(
                    loggedInPlayer = state.loggedInPlayer.copy(
                        country = value.name,
                        state = if (shouldShowBrazilianStateField) selectedStateName else null,
                    ),
                    countryFlag = value.flag,
                    shouldShowBrazilianStateField = shouldShowBrazilianStateField,
                )
            }
            validateData()
        }
    }

    fun onBrazilianStateSelected(value: BrazilianState) {
        state.value.asContent()?.let { state ->
            updateState {
                state.copy(
                    loggedInPlayer = state.loggedInPlayer.copy(
                        state = value.name,
                    ),
                    selectedBrazilianStateAbbreviation = value.abbreviation,
                )
            }
            validateData()
        }
    }

    fun onCityInputChange(value: String) {
        state.value.asContent()?.let { state ->
            updateState {
                state.copy(
                    loggedInPlayer = state.loggedInPlayer.copy(
                        city = value,
                    )
                )
            }
            validateData()
        }
    }

    fun onUploadButtonTapped() {
        val state = state.value.asContent()

        state?.let { content ->
            updateState {
                content.copy(
                    isUploadingData = true,
                    canUploadData = false,
                )
            }

            viewModelScope.launch {
                with (content.loggedInPlayer) {
                    val result = updatePlayerData(
                        updatePlayerDataRequest = UpdatePlayerDataRequest(
                            playerId = playerId,
                            updatedPlayerData = UpdatedPlayerData(
                                name = name,
                                nickname = nickname,
                                birthday = birthday,
                                favoriteTeam = favoriteTeam,
                                city = city,
                                state = this.state,
                                country = country,
                            )
                        )
                    )

                    when (result) {
                        is ServiceResult.Success -> {
                            storeLoggedInPlayer(loggedInPlayer = result.response)
                            emitSideEffect(PersonalDataSideEffect.CloseScreen)
                        }
                        is ServiceResult.Error -> {}
                    }

                    updateState {
                        content.copy(
                            isUploadingData = false,
                        )
                    }
                }
            }
        }
    }

    private fun validateData() {
        state.value.asContent()?.let { state ->
            with (state.loggedInPlayer) {
                val isBrazilianStateValid = if (country == BRAZIL) {
                    !this.state.isNullOrEmpty()
                } else true

                val isDataValid = name.isNotEmpty() &&
                        favoriteTeam.isNotEmpty() &&
                        country.isNotEmpty() &&
                        city.isNotEmpty() &&
                        isBrazilianStateValid

                updateState {
                    state.copy(
                        canUploadData = isDataValid,
                    )
                }
            }
        }
    }

    private companion object {
        const val BRAZIL = "Brasil"
    }
}
