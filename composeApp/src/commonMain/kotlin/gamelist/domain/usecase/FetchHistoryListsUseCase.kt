package gamelist.domain.usecase

import gamelist.domain.model.history.HistoryGameLists
import gamelist.domain.repository.GameListRepository
import network.ServiceResult

class FetchHistoryListsUseCase(
    private val repository: GameListRepository,
) {
    suspend operator fun invoke(): ServiceResult<HistoryGameLists> =
        repository.fetchHistoryGameLists()
}
