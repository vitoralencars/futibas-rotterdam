package gamelist.presentation.model.history

import gamelist.domain.model.history.HistoryGameItem
import player.domain.model.Player

sealed interface HistoryGameListItemViewState {
    val historyGameItem: HistoryGameItem

    data class HiddenList(override val historyGameItem: HistoryGameItem) : HistoryGameListItemViewState

    sealed interface ExpandedList : HistoryGameListItemViewState {
        data class Loading(override val historyGameItem: HistoryGameItem) : ExpandedList
        data class Content(
            override val historyGameItem: HistoryGameItem,
            val players: List<Player>,
        ) : ExpandedList
    }
}
