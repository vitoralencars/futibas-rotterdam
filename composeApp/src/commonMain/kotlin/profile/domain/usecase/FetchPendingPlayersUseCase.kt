package profile.domain.usecase

import profile.domain.repository.ProfileRepository

class FetchPendingPlayersUseCase(
    private val repository: ProfileRepository,
) {
    suspend operator fun invoke() = repository.fetchPendingPlayers()
}
