package gamelist.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import common.base.BaseViewModel
import components.button.ButtonViewState
import gamelist.domain.model.current.CurrentGameList
import gamelist.domain.model.current.GameListPlayerStatus
import gamelist.domain.model.current.NewPlayerListStatus
import gamelist.domain.usecase.FetchCurrentGameListUseCase
import gamelist.domain.usecase.FetchHistoryListsUseCase
import gamelist.domain.usecase.UpdatePlayerListStatusUseCase
import gamelist.presentation.model.GameListScreenState
import gamelist.presentation.model.GameListTabViewState
import gamelist.presentation.model.current.LoggedInPlayerGameListStatus
import gamelist.presentation.model.history.HistoryGameListItemViewState
import kotlinx.coroutines.launch
import network.ServiceResult
import player.domain.mapper.toPLayerLevel
import player.domain.model.LoggedInPlayer
import player.domain.model.PlayerLevel
import player.domain.usecase.FetchPlayersUseCase
import player.domain.usecase.RetrieveLoggedInPlayerUseCase

class GameListViewModel(
    private val fetchCurrentGameListUseCase: FetchCurrentGameListUseCase,
    private val fetchHistoryListsUseCase: FetchHistoryListsUseCase,
    private val fetchPlayersUseCase: FetchPlayersUseCase,
    private val retrieveLoggedInPlayerUseCase: RetrieveLoggedInPlayerUseCase,
    private val updatePlayerListStatusUseCase: UpdatePlayerListStatusUseCase,
): BaseViewModel<GameListScreenState, GameListSideEffect>(
    initialState = GameListScreenState.Loading
) {

    init {
        fetchCurrentGameList()
    }

    override fun onViewResumed() {
        state.value.asContent()?.let {
            if (it.currentGameListViewState.asContent() == null) {
                fetchCurrentGameList()
            }
        }
    }

    private fun fetchCurrentGameList() {
        updateState { GameListScreenState.Loading }
        viewModelScope.launch {
            val currentGameListViewState = when (
                val currentGameListResult = fetchCurrentGameListUseCase()
            ) {
                is ServiceResult.Success -> {
                    currentGameListResult.response?.let {
                        val loggedInPlayerGameListStatus = getLoggedInPlayerListStatus(
                            currentGameList = it,
                            loggedInPlayer = retrieveLoggedInPlayerUseCase(),
                        )
                        GameListTabViewState.CurrentGameListViewState.Content(
                            tabTitle = it.date,
                            currentGameList = it,
                            loggedInPlayerGameListStatus = loggedInPlayerGameListStatus,
                            inButtonViewState = getInButtonViewState(loggedInPlayerGameListStatus),
                            outButtonViewState = getOutButtonViewState(loggedInPlayerGameListStatus),
                            isRefreshing = false,
                        )
                    } ?: GameListTabViewState.CurrentGameListViewState.Empty()
                }
                is ServiceResult.Error -> {
                    GameListTabViewState.CurrentGameListViewState.Error()
                }
            }
            updateState {
                GameListScreenState.Content(
                    currentGameListViewState = currentGameListViewState
                )
            }
        }
    }

    fun refreshCurrentGameList() {
        val content = state.value.asContent()
        val currentGameListContent = content?.currentGameListViewState?.asContent()

        if (content != null && currentGameListContent != null) {
            updateState {
                content.copy(
                    currentGameListViewState = currentGameListContent.copy(
                        isRefreshing = true,
                    )
                )
            }
            fetchCurrentGameList()
        }
    }

    private fun getLoggedInPlayerListStatus(
        currentGameList: CurrentGameList,
        loggedInPlayer: LoggedInPlayer?,
    ): LoggedInPlayerGameListStatus {
        return loggedInPlayer?.let { player ->
            when (player.playerId) {
                in currentGameList.playersIn.players.map { it.playerId } -> {
                    when (player.level.toPLayerLevel()) {
                        PlayerLevel.SPOT -> LoggedInPlayerGameListStatus.SPOT_IN
                        else -> LoggedInPlayerGameListStatus.MONTHLY_IN
                    }
                }
                in currentGameList.playersSpot.players.map { it.playerId } -> {
                    when (player.level.toPLayerLevel()) {
                        PlayerLevel.SPOT -> LoggedInPlayerGameListStatus.SPOT_SPOT
                        else -> LoggedInPlayerGameListStatus.MONTHLY_SPOT
                    }
                }
                in currentGameList.playersOut.players.map { it.playerId } -> {
                    LoggedInPlayerGameListStatus.MONTHLY_OUT
                }
                else -> {
                    when (player.level.toPLayerLevel()) {
                        PlayerLevel.SPOT -> LoggedInPlayerGameListStatus.SPOT_NOT_ANSWERED
                        else -> LoggedInPlayerGameListStatus.MONTHLY_NOT_ANSWERED
                    }
                }
            }
        } ?: LoggedInPlayerGameListStatus.SPOT_NOT_ANSWERED
    }

    private fun getInButtonViewState(
        loggedInPlayerGameListStatus: LoggedInPlayerGameListStatus,
    ): ButtonViewState? {
        return when (loggedInPlayerGameListStatus) {
            LoggedInPlayerGameListStatus.SPOT_NOT_ANSWERED,
            LoggedInPlayerGameListStatus.MONTHLY_OUT,
            LoggedInPlayerGameListStatus.MONTHLY_NOT_ANSWERED, -> {
                ButtonViewState.Content(
                    text = "IN",
                )
            }
            else -> null
        }
    }

    private fun getOutButtonViewState(
        loggedInPlayerGameListStatus: LoggedInPlayerGameListStatus
    ): ButtonViewState? {
        return when (loggedInPlayerGameListStatus) {
            LoggedInPlayerGameListStatus.SPOT_IN,
            LoggedInPlayerGameListStatus.SPOT_SPOT,
            LoggedInPlayerGameListStatus.MONTHLY_IN,
            LoggedInPlayerGameListStatus.MONTHLY_SPOT,
            LoggedInPlayerGameListStatus.MONTHLY_NOT_ANSWERED, -> {
                ButtonViewState.Content(
                    text = "OUT",
                )
            }
            else -> null
        }
    }

    fun fetchHistoryGameLists() {
        val screenState = state.value
        if (screenState is GameListScreenState.Content &&
            screenState.historyGameListsViewState !is GameListTabViewState.HistoryGameListsViewState.Content) {

            updateState {
                screenState.copy(
                    historyGameListsViewState = GameListTabViewState.HistoryGameListsViewState.Loading()
                )
            }
            viewModelScope.launch {
                val historyGameListsViewState = when (
                    val historyGameListsResult = fetchHistoryListsUseCase()
                ) {
                    is ServiceResult.Success -> {
                        val lists = historyGameListsResult.response.historyGameLists
                        if (lists.isEmpty()) {
                            GameListTabViewState.HistoryGameListsViewState.Empty()
                        } else {
                            GameListTabViewState.HistoryGameListsViewState.Content(
                                historyListViewStates = lists.map {
                                    HistoryGameListItemViewState.HiddenList(
                                        historyGameItem = it,
                                    )
                                }
                            )
                        }
                    }
                    is ServiceResult.Error -> GameListTabViewState.HistoryGameListsViewState.Error()
                }
                updateState {
                    screenState.copy(
                        historyGameListsViewState = historyGameListsViewState,
                    )
                }
            }
        }
    }

    fun onHistoryGameListItemTapped(item: HistoryGameListItemViewState) {
        val screenState = state.value
        if (screenState is GameListScreenState.Content &&
            screenState.historyGameListsViewState is GameListTabViewState.HistoryGameListsViewState.Content) {

            if (item is HistoryGameListItemViewState.ExpandedList) {
                updateState {
                    screenState.copy(
                        historyGameListsViewState = screenState.historyGameListsViewState.copy(
                            historyListViewStates = screenState.historyGameListsViewState.historyListViewStates.map {
                                HistoryGameListItemViewState.HiddenList(it.historyGameItem)
                            }
                        )
                    )
                }
            } else {
                val historyGameItem = item.historyGameItem

                updateState {
                    screenState.copy(
                        historyGameListsViewState = screenState.historyGameListsViewState.copy(
                            historyListViewStates = screenState.historyGameListsViewState.historyListViewStates.map {
                                if (it == item) {
                                    HistoryGameListItemViewState.ExpandedList.Loading(historyGameItem)
                                } else {
                                    HistoryGameListItemViewState.HiddenList(it.historyGameItem)
                                }
                            }
                        )
                    )
                }

                viewModelScope.launch {
                    val historyGameListItemViewState = when (
                        val playersResult = fetchPlayersUseCase(item.historyGameItem.playersIds)
                    ) {
                        is ServiceResult.Success -> {
                            HistoryGameListItemViewState.ExpandedList.Content(
                                historyGameItem = historyGameItem,
                                players = playersResult.response,
                            )
                        }
                        is ServiceResult.Error -> {
                            HistoryGameListItemViewState.HiddenList(historyGameItem)
                        }
                    }

                    updateState {
                        screenState.copy(
                            historyGameListsViewState = screenState.historyGameListsViewState.copy(
                                historyListViewStates = screenState.historyGameListsViewState.historyListViewStates.map {
                                    if (it == item) {
                                        historyGameListItemViewState
                                    } else {
                                        HistoryGameListItemViewState.HiddenList(it.historyGameItem)
                                    }
                                }
                            )
                        )
                    }
                }
            }
        }
    }

    fun onTabSelected(index: Int) {
        updateState { state ->
            (state as GameListScreenState.Content).copy(
                selectedTabIndex = index
            )
        }
    }

    fun updateOutAlertDialogVisibility() {
        val screenState = state.value.asContent()
        val currentGameListView = screenState?.currentGameListViewState?.asContent()

        if (screenState != null && currentGameListView != null) {
            updateState {
                screenState.copy(
                    currentGameListViewState = currentGameListView.copy(
                        shouldShowOutConfirmDialog = !currentGameListView.shouldShowOutConfirmDialog,
                    )
                )
            }
        }
    }

    fun updateLoggedInPlayerListStatus(
        answer: GameListPlayerStatus,
    ) {
        updateState {
            val screenState = it.asContent()
            val currentGameListView = screenState?.currentGameListViewState?.asContent()

            screenState?.let {
                screenState.copy(
                    currentGameListViewState = currentGameListView?.copy(
                        inButtonViewState = currentGameListView.inButtonViewState?.let {
                            ButtonViewState.Loading
                        },
                        outButtonViewState = currentGameListView.outButtonViewState?.let {
                            ButtonViewState.Loading
                        },
                    ) ?: screenState.currentGameListViewState
                )
            } ?: it
        }
        viewModelScope.launch {
            retrieveLoggedInPlayerUseCase()?.let { loggedInPlayer ->
                updatePlayerListStatusUseCase(
                    newPlayerListStatus = NewPlayerListStatus(
                        status = answer,
                        loggedInPlayer = loggedInPlayer,
                    )
                )
            }
            fetchCurrentGameList()
        }
    }
}
