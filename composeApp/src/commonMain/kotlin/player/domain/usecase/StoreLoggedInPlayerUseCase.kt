package player.domain.usecase

import player.domain.model.LoggedInPlayer
import player.domain.repository.PlayerRepository

class StoreLoggedInPlayerUseCase(
    private val repository: PlayerRepository,
) {

    suspend operator fun invoke(loggedInPlayer: LoggedInPlayer) {
        repository.storeLoggedInPlayer(loggedInPlayer)
    }
}
