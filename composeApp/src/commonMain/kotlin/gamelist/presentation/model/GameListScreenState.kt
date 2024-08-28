package gamelist.presentation.model

sealed interface GameListScreenState {
    data object Loading : GameListScreenState

    data class Content(
        val currentGameListViewState: GameListTabViewState.CurrentGameListViewState,
        val historyGameListsViewState: GameListTabViewState.HistoryGameListsViewState =
            GameListTabViewState.HistoryGameListsViewState.Empty(),
        val selectedTabIndex: Int = 0,
    ) : GameListScreenState

    fun asContent() = this as? Content
}
