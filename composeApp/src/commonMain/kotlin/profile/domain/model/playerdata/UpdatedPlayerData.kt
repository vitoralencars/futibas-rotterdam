package profile.domain.model.playerdata

import kotlinx.serialization.Serializable

@Serializable
data class UpdatedPlayerData(
    val name: String,
    val nickname: String?,
    val birthday: String?,
    val favoriteTeam: String,
    val city: String,
    val state: String?,
    val country: String,
)
