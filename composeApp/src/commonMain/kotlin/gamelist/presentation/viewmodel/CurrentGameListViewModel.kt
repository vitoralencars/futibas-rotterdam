package gamelist.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import common.base.BaseViewModel
import gamelist.domain.usecase.FetchCurrentGameListUseCase
import gamelist.presentation.model.GameListTabViewState
import kotlinx.coroutines.launch
import network.ServiceResult

class CurrentGameListViewModel(
    private val fetchCurrentGameList: FetchCurrentGameListUseCase,
) : BaseViewModel<GameListTabViewState.CurrentGameListViewState, Nothing>(
    initialState = GameListTabViewState.CurrentGameListViewState.Empty()
) {
    init {
        /*viewModelScope.launch {
            val stateResult = when (val currentGameList = fetchCurrentGameList()) {
                is ServiceResult.Success -> {
                    currentGameList.response?.let {
                        GameListTabViewState.CurrentGameListViewState.Content(
                            tabTitle = it.date,
                            currentGameList = it,
                            loggedInPlayerGameListStatus =
                        )
                    } ?: GameListTabViewState.CurrentGameListViewState.Empty()
                }
                is ServiceResult.Error -> {
                    GameListTabViewState.CurrentGameListViewState.Error()
                }
            }
            updateState {
                stateResult
            }
        }*/
    }
}
