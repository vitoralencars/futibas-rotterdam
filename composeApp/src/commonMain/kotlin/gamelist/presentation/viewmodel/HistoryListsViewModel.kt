package gamelist.presentation.viewmodel

import common.base.BaseViewModel
import gamelist.domain.usecase.FetchHistoryListsUseCase
import gamelist.presentation.model.GameListTabViewState

class HistoryListsViewModel(
    fetchHistoryListsUseCase: FetchHistoryListsUseCase
) : BaseViewModel<GameListTabViewState.HistoryGameListsViewState, Nothing>(
    initialState = GameListTabViewState.HistoryGameListsViewState.Loading()
) {
    init {
        /*viewModelScope.launch {
            val historyLists = fetchHistoryListsUseCase()
            delay(1500L)
            updateState {
                HistoryListsViewState.Content(
                    historyListViewStates = historyLists.map {
                        HistoryListItemViewState(
                            date = it.date,
                            players = it.players,
                        )
                    }
                )
            }
        }*/
    }

    /*fun onListItemTapped(itemTappedIndex: Int) {
        (state.value as? HistoryGameListsViewState.Content)?.let { state ->
            updateState {
                state.copy(
                    historyListViewStates = state.historyListViewStates.mapIndexed { index, historyListItemViewState ->
                        if (itemTappedIndex == index) {
                            historyListItemViewState.copy(
                                isExpanded = !historyListItemViewState.isExpanded
                            )
                        } else {
                            historyListItemViewState
                        }
                    }
                )
            }
        }
    }*/
}
