package profile.data.model

import player.domain.model.LoggedInPlayer
import kotlinx.serialization.Serializable

@Serializable
data class LoggedInPlayerData(
    val loggedInPlayer: LoggedInPlayer?,
)
