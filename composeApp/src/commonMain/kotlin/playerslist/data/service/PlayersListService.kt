package playerslist.data.service

import network.ServiceResult
import playerslist.data.model.PlayersListData

interface PlayersListService {

    suspend fun fetchPlayers(): ServiceResult<PlayersListData>
}
