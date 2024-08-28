package playerslist.data.model

import kotlinx.serialization.Serializable
import player.data.model.PlayerData

@Serializable
data class PlayersListData(
    val players: List<PlayerData>
)
