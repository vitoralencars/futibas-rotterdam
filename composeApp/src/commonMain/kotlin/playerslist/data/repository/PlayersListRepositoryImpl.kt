package playerslist.data.repository

import player.domain.model.Player
import network.ServiceResult
import player.data.mapper.PlayerDataToPlayerDomainMapper
import playerslist.data.service.PlayersListService
import playerslist.domain.repository.PlayersListRepository

class PlayersListRepositoryImpl(
    private val service: PlayersListService,
    private val mapper: PlayerDataToPlayerDomainMapper,
): PlayersListRepository {
    override suspend fun fetchPlayers(): ServiceResult<List<Player>> {
        return when (val playersResponse = service.fetchPlayers()) {
            is ServiceResult.Success -> {
                ServiceResult.Success(playersResponse.response.players.map {
                    mapper(it)
                })
            }
            is ServiceResult.Error -> ServiceResult.Error(playersResponse.exception)
        }
    }
}
