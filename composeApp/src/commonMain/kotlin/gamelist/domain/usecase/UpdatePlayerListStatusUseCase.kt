package gamelist.domain.usecase

import gamelist.domain.model.current.NewPlayerListStatus
import gamelist.domain.repository.GameListRepository

class UpdatePlayerListStatusUseCase(private val repository: GameListRepository) {
    suspend operator fun invoke(
        newPlayerListStatus: NewPlayerListStatus,
    ) = repository.updatePlayerListStatus(newPlayerListStatus)
}
