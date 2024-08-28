package gamelist.data.model.history

import kotlinx.serialization.Serializable

@Serializable
data class HistoryGameListsData(
    val historyGameLists: List<HistoryGameListDataItem>
)

@Serializable
data class HistoryGameListDataItem(
    val gameId: String,
    val playersInIds: List<String>,
    val date: String,
    val location: String,
)
