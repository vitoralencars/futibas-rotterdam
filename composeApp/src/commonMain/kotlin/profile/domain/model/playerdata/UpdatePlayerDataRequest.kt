package profile.domain.model.playerdata

import kotlinx.serialization.Serializable

@Serializable
data class UpdatePlayerDataRequest(
    val playerId: String,
    val updatedPlayerData: UpdatedPlayerData,
)
