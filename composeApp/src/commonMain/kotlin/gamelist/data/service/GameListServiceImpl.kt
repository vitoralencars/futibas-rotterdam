package gamelist.data.service

import gamelist.data.model.current.CurrentGameListResponseData
import gamelist.data.model.current.UpdatePlayerListStatusRequest
import gamelist.data.model.history.HistoryGameListsData
import network.ServiceHandler
import network.ServiceResult

class GameListServiceImpl(
    private val serviceHandler: ServiceHandler,
) : GameListService {

    override suspend fun fetchCurrentGameList(): ServiceResult<CurrentGameListResponseData?> {
        return serviceHandler.performServiceCall(
            url = "https://sx0wqff9f6.execute-api.sa-east-1.amazonaws.com/default/getCurrentGameList"
        )
    }

    override suspend fun fetchHistoryGameLists(): ServiceResult<HistoryGameListsData> {
        return serviceHandler.performServiceCall(
            url = "https://wq9l7m6d7f.execute-api.sa-east-1.amazonaws.com/default/getFutibasGamesLists"
        )
    }

    override suspend fun updatePlayerListStatus(
        updatePlayerListStatusRequest: UpdatePlayerListStatusRequest
    ): ServiceResult<Any?> {
        return serviceHandler.performServiceCall(
            url = "https://gfudq3pd4c.execute-api.sa-east-1.amazonaws.com/default/updatePlayerListStatus",
            body = updatePlayerListStatusRequest,
        )
    }
}
