package player.domain.usecase

import player.domain.model.NewPlayerLevel
import player.domain.model.PlayerLevel
import player.domain.repository.PlayerRepository

class UpdatePlayerLevelUseCase(private val playerRepository: PlayerRepository) {

    suspend operator fun invoke(
        playerId: String,
        newPlayerLevel: PlayerLevel,
    ) = playerRepository.updatePlayerLevel(newPlayerLevel = NewPlayerLevel(
        playerId = playerId,
        newPlayerLevel = newPlayerLevel.indicator,
    ))
}
