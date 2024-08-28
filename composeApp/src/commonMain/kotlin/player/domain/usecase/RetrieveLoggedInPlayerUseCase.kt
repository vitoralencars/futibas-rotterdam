package player.domain.usecase

import player.domain.model.LoggedInPlayer
import player.domain.repository.PlayerRepository

class RetrieveLoggedInPlayerUseCase(private val repository: PlayerRepository) {

    suspend operator fun invoke(): LoggedInPlayer? = repository.retrieveLoggedInPlayer()
}
