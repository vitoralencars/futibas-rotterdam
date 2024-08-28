package playerslist.data.service

import network.ServiceHandler
import network.ServiceResult
import playerslist.data.model.PlayersListData

class PlayersListServiceImpl(
    private val serviceHandler: ServiceHandler,
) : PlayersListService {
    override suspend fun fetchPlayers(): ServiceResult<PlayersListData> {
        return serviceHandler.performServiceCall(
            url = "https://sjm2ycvkg7.execute-api.sa-east-1.amazonaws.com/default/getFutibasPlayers",
        )
    }
}
