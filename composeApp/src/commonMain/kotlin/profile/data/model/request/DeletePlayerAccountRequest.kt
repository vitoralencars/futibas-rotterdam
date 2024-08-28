package profile.data.model.request

import kotlinx.serialization.Serializable

@Serializable
data class DeletePlayerAccountRequest(
    private val playerId: String,
)
