package player.domain.model

import common.model.Country
import kotlinx.serialization.Serializable

@Serializable
data class Player(
    val playerId: String,
    val name: String,
    val nickname: String? = null,
    val birthday: String? = null,
    val age: Int,
    val favoriteTeam: String,
    val level: PlayerLevel,
    val city: String,
    val country: Country,
    val photoUrl: String? = null,
)
