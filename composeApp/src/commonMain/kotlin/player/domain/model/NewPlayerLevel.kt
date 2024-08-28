package player.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class NewPlayerLevel(
    private val playerId: String,
    private val newPlayerLevel: Int,
)
