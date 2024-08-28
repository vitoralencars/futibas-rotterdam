package player.data.model

import kotlinx.serialization.Serializable

@Serializable
data class PlayersDataList(
    val players: List<PlayerData>,
)
