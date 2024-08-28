package player.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class PlayersIds(
    val playersIds: List<String>,
)
