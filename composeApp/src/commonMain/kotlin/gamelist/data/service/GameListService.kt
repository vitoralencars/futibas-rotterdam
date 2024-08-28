package gamelist.data.service

import gamelist.data.model.current.CurrentGameListResponseData
import gamelist.data.model.current.UpdatePlayerListStatusRequest
import gamelist.data.model.history.HistoryGameListsData
import network.ServiceResult

interface GameListService {

    suspend fun fetchCurrentGameList(): ServiceResult<CurrentGameListResponseData?>

    suspend fun fetchHistoryGameLists(): ServiceResult<HistoryGameListsData>

    suspend fun updatePlayerListStatus(
        updatePlayerListStatusRequest: UpdatePlayerListStatusRequest,
    ): ServiceResult<Any?>
}
