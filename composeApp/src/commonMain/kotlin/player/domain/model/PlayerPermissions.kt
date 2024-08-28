package player.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class PlayerPermissions(
    val configureList: Boolean,
    val managePendingPlayers: Boolean,
    val updatePlayerLevel: Boolean,
)
