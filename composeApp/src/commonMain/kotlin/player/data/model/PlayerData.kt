package player.data.model

import kotlinx.serialization.Serializable

@Serializable
data class PlayerData(
    val playerId: String,
    val name: String,
    val nickname: String? = null,
    val birthday: String? = null,
    val age: Int,
    val favoriteTeam: String,
    val level: Int,
    val city: String,
    val state: String? = null,
    val country: String,
    val photoUrl: String? = null,
)
