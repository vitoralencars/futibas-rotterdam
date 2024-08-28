package player.domain.usecase

import network.ServiceResult
import player.domain.model.Player
import player.domain.model.PlayersIds
import player.domain.repository.PlayerRepository

class FetchPlayersUseCase(private val playerRepository: PlayerRepository) {
    suspend operator fun invoke(playersIds: List<String>): ServiceResult<List<Player>> {
        return playerRepository.fetchPlayersList(PlayersIds(playersIds))
    }
}
