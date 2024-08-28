package playerslist.domain.repository

import network.ServiceResult
import player.domain.model.Player

interface PlayersListRepository {
    suspend fun fetchPlayers(): ServiceResult<List<Player>>
}
