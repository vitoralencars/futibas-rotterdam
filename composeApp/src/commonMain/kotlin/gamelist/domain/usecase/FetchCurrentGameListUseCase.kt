package gamelist.domain.usecase

import gamelist.domain.model.current.CurrentGameList
import gamelist.domain.repository.GameListRepository
import network.ServiceResult

class FetchCurrentGameListUseCase(private val repository: GameListRepository) {
    suspend operator fun invoke(): ServiceResult<CurrentGameList?> = repository.fetchCurrentGameList()
}
