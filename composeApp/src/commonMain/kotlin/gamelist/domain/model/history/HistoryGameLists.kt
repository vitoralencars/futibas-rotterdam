package gamelist.domain.model.history

data class HistoryGameLists(
    val historyGameLists: List<HistoryGameItem>,
)

data class HistoryGameItem(
    val gameId: String,
    val playersIds: List<String>,
    val date: String,
    val location: String,
)
