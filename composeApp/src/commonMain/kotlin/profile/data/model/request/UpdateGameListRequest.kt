package profile.data.model.request

import kotlinx.serialization.Serializable

@Serializable
data class UpdateGameListRequest(
    private val gameId: String,
    private val gameListData: GameListData,
)

@Serializable
data class GameListData(
    private val date: String,
    private val time: String,
    private val location: String,
    private val maxPlayers: Int,
    private val isActive: Boolean,
)
