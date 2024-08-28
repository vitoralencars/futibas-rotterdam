package gamelist.data.model.current

import kotlinx.serialization.Serializable
import player.data.model.PlayerData

@Serializable
data class CurrentGameListResponseData(
    val gameList: CurrentGameListData?,
)

@Serializable
data class CurrentGameListData(
    val gameId: String,
    val date: String,
    val time: String,
    val location: String,
    val maxPlayers: Int,
    val playersIn: List<PlayerData>,
    val playersOut: List<PlayerData>,
    val playersSpot: List<PlayerData>,
)
