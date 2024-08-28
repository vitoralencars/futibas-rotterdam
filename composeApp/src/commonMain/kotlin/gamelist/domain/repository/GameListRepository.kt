package gamelist.domain.repository

import gamelist.domain.model.current.CurrentGameList
import gamelist.domain.model.current.NewPlayerListStatus
import gamelist.domain.model.history.HistoryGameLists
import network.ServiceResult

interface GameListRepository {
    suspend fun fetchCurrentGameList(): ServiceResult<CurrentGameList?>
    suspend fun fetchHistoryGameLists(): ServiceResult<HistoryGameLists>
    suspend fun updatePlayerListStatus(newPlayerListStatus: NewPlayerListStatus): ServiceResult<Any?>
}
