package login.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginPlayerRequest(
    val playerId: String,
)
