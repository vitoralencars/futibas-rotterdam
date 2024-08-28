package gamelist.data.repository

import gamelist.data.mapper.HistoryGameListsDataToHistoryGameListsDomainMapper
import gamelist.data.mapper.PlayerDataListToCurrentGameListCategoryMapper
import gamelist.data.model.current.UpdatePlayerListStatusRequest
import gamelist.data.service.GameListService
import gamelist.domain.model.current.CurrentGameList
import gamelist.domain.model.current.CurrentGameListType
import gamelist.domain.model.current.NewPlayerListStatus
import gamelist.domain.model.history.HistoryGameLists
import gamelist.domain.repository.GameListRepository
import network.ServiceResult

class GameListRepositoryImpl(
    private val gameListService: GameListService,
    private val playersDataListMapper: PlayerDataListToCurrentGameListCategoryMapper,
    private val historyGameListsMapper: HistoryGameListsDataToHistoryGameListsDomainMapper,
) : GameListRepository {
    override suspend fun fetchCurrentGameList(): ServiceResult<CurrentGameList?> =
        when (val result = gameListService.fetchCurrentGameList()) {
            is ServiceResult.Success -> {
                val gameListData = result.response?.gameList
                ServiceResult.Success(
                    gameListData?.let {
                        with(it) {
                            CurrentGameList(
                                gameId = gameId,
                                date = date,
                                time = time,
                                location = location,
                                maxPlayers = maxPlayers,
                                playersIn = playersDataListMapper(
                                    params = PlayerDataListToCurrentGameListCategoryMapper.Params(
                                        currentGameListType = CurrentGameListType.IN,
                                        playersIn,
                                    )
                                ),
                                playersOut = playersDataListMapper(
                                    params = PlayerDataListToCurrentGameListCategoryMapper.Params(
                                        currentGameListType = CurrentGameListType.OUT,
                                        playersOut,
                                    )
                                ),
                                playersSpot = playersDataListMapper(
                                    params = PlayerDataListToCurrentGameListCategoryMapper.Params(
                                        currentGameListType = CurrentGameListType.SPOT,
                                        playersSpot,
                                    )
                                ),
                            )
                        }
                    }
                )
            }
            is ServiceResult.Error -> result
        }

    override suspend fun fetchHistoryGameLists(): ServiceResult<HistoryGameLists> {
        return when (val result = gameListService.fetchHistoryGameLists()) {
            is ServiceResult.Success -> {
                ServiceResult.Success(
                    response = historyGameListsMapper(result.response),
                )
            }
            is ServiceResult.Error -> result
        }
    }

    override suspend fun updatePlayerListStatus(newPlayerListStatus: NewPlayerListStatus): ServiceResult<Any?> {
        return when (val result = gameListService.updatePlayerListStatus(
            updatePlayerListStatusRequest = UpdatePlayerListStatusRequest(
                playerId = newPlayerListStatus.loggedInPlayer.playerId,
                playerLevel = newPlayerListStatus.loggedInPlayer.level,
                status = newPlayerListStatus.status.value,
            )
        )) {
            is ServiceResult.Success -> ServiceResult.Success(response = null)
            is ServiceResult.Error -> result
        }
    }
}
