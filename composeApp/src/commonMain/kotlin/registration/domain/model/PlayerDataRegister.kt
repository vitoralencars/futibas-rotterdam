package registration.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class PlayerDataRegister(
    val playerId: String,
    val email: String,
    val name: String,
    val nickname: String?,
    val birthday: String?,
    val favoriteTeam: String,
    val city: String,
    val state: String?,
    val country: String,
    val photoData: String?,
)
