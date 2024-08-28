package playerslist.domain.usecase

import playerslist.domain.repository.PlayersListRepository

class FetchPlayersListUseCase(
    private val playersListRepository: PlayersListRepository,
) {
    suspend operator fun invoke() = playersListRepository.fetchPlayers()
}
