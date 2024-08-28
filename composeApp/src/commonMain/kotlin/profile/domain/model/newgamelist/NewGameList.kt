package profile.domain.model.newgamelist

import kotlinx.serialization.Serializable

@Serializable
data class NewGameList(
    val date: String,
    val time: String,
    val location: String,
    val maxPlayers: Int,
)
