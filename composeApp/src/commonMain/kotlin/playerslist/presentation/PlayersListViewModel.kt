package playerslist.presentation

import androidx.lifecycle.viewModelScope
import common.base.BaseViewModel
import kotlinx.coroutines.launch
import network.ServiceResult
import player.domain.model.PlayerLevel
import playerslist.domain.usecase.FetchPlayersListUseCase
import playerslist.domain.usecase.GetSegmentedPlayersListUseCase

class PlayersListViewModel(
    private val fetchPlayersList: FetchPlayersListUseCase,
    private val getSegmentedPlayersList: GetSegmentedPlayersListUseCase,
) : BaseViewModel<PlayersListScreenState, PlayersListSideEffect>(
    initialState = PlayersListScreenState.Loading()
) {

    init {
        fetchPlayers()
    }

    private fun fetchPlayers() {
        viewModelScope.launch {
            val response = fetchPlayersList()
            updateState {
                when (response) {
                    is ServiceResult.Success -> {
                        val playersList = response.response

                        PlayersListScreenState.Content(
                            presidentList = getSegmentedPlayersList(
                                playersList = playersList,
                                PlayerLevel.PRESIDENT,
                            ),
                            boardList = getSegmentedPlayersList(
                                playersList = playersList,
                                PlayerLevel.BOARD,
                            ),
                            monthlyList = getSegmentedPlayersList(
                                playersList = playersList,
                                PlayerLevel.MONTHLY,
                            ),
                            spotList = getSegmentedPlayersList(
                                playersList = playersList,
                                PlayerLevel.SPOT,
                            ),
                        )
                    }

                    is ServiceResult.Error -> PlayersListScreenState.Error()
                }.setRefreshing(false)
            }
        }
    }

    fun refreshPlayersList() {
        updateState {
            it.setRefreshing(isRefreshing = true)
        }
        fetchPlayers()
    }
}
