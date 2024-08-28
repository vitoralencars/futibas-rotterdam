package player.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class LoggedInPlayer(
    val playerId: String,
    val email: String,
    val name: String,
    val level: Int,
    val nickname: String?,
    val birthday: String?,
    val favoriteTeam: String,
    val city: String,
    val state: String?,
    val country: String,
    val photoUrl: String?,
    val fcmToken: String,
    val permissions: PlayerPermissions,
)
