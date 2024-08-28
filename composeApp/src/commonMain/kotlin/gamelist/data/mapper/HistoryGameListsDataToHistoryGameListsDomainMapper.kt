package gamelist.data.mapper

import gamelist.data.model.history.HistoryGameListDataItem
import gamelist.data.model.history.HistoryGameListsData
import gamelist.domain.model.history.HistoryGameItem
import gamelist.domain.model.history.HistoryGameLists

class HistoryGameListsDataToHistoryGameListsDomainMapper {

    operator fun invoke(data: HistoryGameListsData): HistoryGameLists {
        return HistoryGameLists(
            historyGameLists = data.historyGameLists.map { it.toDomain() }
        )
    }

    private fun HistoryGameListDataItem.toDomain() = with(this) {
        HistoryGameItem(
            gameId = gameId,
            playersIds = playersInIds,
            date = date,
            location = location,
        )
    }
}
