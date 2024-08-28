package gamelist.data.model.current

import kotlinx.serialization.Serializable

@Serializable
data class UpdatePlayerListStatusRequest(
    val playerId: String,
    val playerLevel: Int,
    val status: Int,
)
