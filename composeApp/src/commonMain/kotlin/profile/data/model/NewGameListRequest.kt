package profile.data.model

import kotlinx.serialization.Serializable
import profile.domain.model.newgamelist.NewGameList

@Serializable
data class NewGameListRequest(
    val newGameList: NewGameList
)
