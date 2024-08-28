package gamelist.domain.model.current

data class CurrentGameList(
    val gameId: String,
    val date: String,
    val time: String,
    val location: String,
    val maxPlayers: Int,
    val playersIn: CurrentGameListCategory,
    val playersOut: CurrentGameListCategory,
    val playersSpot: CurrentGameListCategory,
)
