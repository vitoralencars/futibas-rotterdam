package profile.presentation.menu.newgamelist

import androidx.lifecycle.viewModelScope
import common.base.BaseViewModel
import gamelist.domain.usecase.FetchCurrentGameListUseCase
import kotlinx.coroutines.launch
import network.ServiceResult
import profile.data.model.request.GameListData
import profile.data.model.request.UpdateGameListRequest
import profile.domain.model.newgamelist.NewGameList
import profile.domain.usecase.CreateGameListUseCase
import profile.domain.usecase.GetCourtsUseCase
import profile.domain.usecase.UpdateGameListUseCase

class NewGameListViewModel(
    private val createGameListUseCase: CreateGameListUseCase,
    private val getCourtsUseCase: GetCourtsUseCase,
    private val fetchCurrentGameListUseCase: FetchCurrentGameListUseCase,
    private val updateGameListUseCase: UpdateGameListUseCase,
) : BaseViewModel<NewGameListScreenState, NewGameListSideEffect>(
    initialState = NewGameListScreenState.Loading
) {

    init {
        fetchCurrentGameList()
    }

    private fun fetchCurrentGameList() {
        viewModelScope.launch {
            when (val currentGameList = fetchCurrentGameListUseCase()) {
                is ServiceResult.Success -> {
                    val courts = getCourtsUseCase()
                    val content = currentGameList.response?.let {
                        with (it) {
                            NewGameListScreenState.Content(
                                gameId = it.gameId,
                                date = date,
                                time = time,
                                location = location,
                                maxPlayers = maxPlayers,
                                courts = courts,
                            )
                        }
                    } ?: NewGameListScreenState.Content(
                        gameId = null,
                        courts = courts,
                    )
                    updateState { content }
                }
                is ServiceResult.Error -> {}
            }
        }
    }

    fun onDatePickerTapped() {
        (state.value.asContent())?.let { state ->
            updateState {
                state.copy(
                    shouldShowDatePicker = true,
                )
            }
        }
    }

    fun onDatePickerDismissed() {
        (state.value.asContent())?.let { state ->
            updateState {
                state.copy(
                    shouldShowDatePicker = false,
                )
            }
        }
    }

    fun onDateSelected(value: String) {
        (state.value.asContent())?.let { state ->
            updateState {
                state.copy(
                    date = value,
                    shouldShowDatePicker = false,
                )
            }
            validateData()
        }
    }

    fun onTimeFieldTapped() {
        (state.value.asContent())?.let { state ->
            updateState {
                state.copy(
                    shouldShowTimePicker = true,
                )
            }
        }
    }

    fun onTimeFieldDismissed() {
        (state.value.asContent())?.let { state ->
            updateState {
                state.copy(
                    shouldShowTimePicker = false,
                )
            }
        }
    }

    fun onTimeSelected(value: String) {
        (state.value.asContent())?.let { state ->
            updateState {
                state.copy(
                    time = value.formatTime(),
                    shouldShowTimePicker = false,
                )
            }
            validateData()
        }
    }

    private fun String.formatTime(): String {
        val (hour, minute) = this.split(":")
        val formattedHour = if (hour.toInt() < 10) "0$hour" else hour
        val formattedMinute = if (minute.toInt() < 10) "0$minute" else minute

        return "$formattedHour:$formattedMinute"
    }

    fun onCourtSelected(value: String) {
        (state.value.asContent())?.let { state ->
            updateState {
                state.copy(
                    location = value,
                )
            }
            validateData()
        }
    }

    fun onMaxPlayersSet(value: String) {
        (state.value.asContent())?.let { state ->
            updateState {
                state.copy(
                    maxPlayers = if (value.isNotBlank()) value.toInt() else -1,
                )
            }
            validateData()
        }
    }

    fun createGameList() {
        (state.value.asContent())?.let { state ->
            updateState {
                state.copy(
                    isConfiguringList = true,
                )
            }
            viewModelScope.launch {
                with (state) {
                    val result = createGameListUseCase(
                        newGameList = NewGameList(
                            date = date,
                            location = location,
                            time = time,
                            maxPlayers = maxPlayers,
                        )
                    )
                    if (result is ServiceResult.Success) {
                        emitSideEffect(NewGameListSideEffect.CloseScreen)
                    } else {
                        updateState {
                            state.copy(
                                isConfiguringList = false,
                            )
                        }
                    }
                }
            }
        }
    }

    fun updateGameList(isActive: Boolean = true) {
        (state.value.asContent())?.let { state ->
            updateState {
                state.copy(
                    isConfiguringList = true,
                )
            }
            viewModelScope.launch {
                with (state) {
                    val result = updateGameListUseCase(
                        updateGameListRequest = UpdateGameListRequest(
                            gameId = gameId.orEmpty(),
                            gameListData = GameListData(
                                date = date,
                                location = location,
                                time = time,
                                maxPlayers = maxPlayers,
                                isActive = isActive,
                            )
                        ),
                    )
                    if (result is ServiceResult.Success) {
                        emitSideEffect(NewGameListSideEffect.CloseScreen)
                    } else {
                        updateState {
                            state.copy(
                                isConfiguringList = false,
                            )
                        }
                    }
                }
            }
        }
    }

    fun closeGameList() {
        updateGameList(isActive = false)
    }

    private fun validateData() {
        (state.value.asContent())?.let { state ->
            val isDataValid = with(state) {
                date.isNotBlank() && time.isNotBlank() && location.isNotBlank() && maxPlayers > 0
            }
            updateState {
                state.copy(
                    canCreateList = isDataValid,
                )
            }
        }
    }
}
