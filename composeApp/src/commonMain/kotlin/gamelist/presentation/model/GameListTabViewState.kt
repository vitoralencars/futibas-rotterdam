package gamelist.presentation.model

import components.button.ButtonViewState
import gamelist.domain.model.current.CurrentGameList
import gamelist.presentation.model.current.LoggedInPlayerGameListStatus
import gamelist.presentation.model.history.HistoryGameListItemViewState

sealed interface GameListTabViewState {
    val tabTitle: String

    sealed interface CurrentGameListViewState : GameListTabViewState {
        data class Error(
            override val tabTitle: String = ""
        ) : CurrentGameListViewState
        data class Empty(
            override val tabTitle: String = ""
        ) : CurrentGameListViewState
        data class Content(
            override val tabTitle: String,
            val currentGameList: CurrentGameList,
            val loggedInPlayerGameListStatus: LoggedInPlayerGameListStatus,
            val inButtonViewState: ButtonViewState?,
            val outButtonViewState: ButtonViewState?,
            val isRefreshing: Boolean = false,
            val shouldShowOutConfirmDialog: Boolean = false,
        ) : CurrentGameListViewState

        fun asContent() = this as? Content
    }

    sealed interface HistoryGameListsViewState : GameListTabViewState {
        data class Loading(
            override val tabTitle: String = "Histórico"
        ) : HistoryGameListsViewState
        data class Error(
            override val tabTitle: String = "Histórico"
        ) : HistoryGameListsViewState
        data class Empty(
            override val tabTitle: String = "Histórico"
        ) : HistoryGameListsViewState
        data class Content(
            override val tabTitle: String = "Histórico",
            val historyListViewStates: List<HistoryGameListItemViewState>
        ) : HistoryGameListsViewState
    }
}
