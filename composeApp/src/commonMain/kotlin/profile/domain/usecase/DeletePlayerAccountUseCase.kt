package profile.domain.usecase

import profile.data.model.request.DeletePlayerAccountRequest
import profile.domain.repository.ProfileRepository

class DeletePlayerAccountUseCase (
    private val repository: ProfileRepository,
) {
    suspend operator fun invoke(playerId: String) =
        repository.deletePlayerAccount(
            deletePlayerAccountRequest = DeletePlayerAccountRequest(
                playerId = playerId,
            ),
        )
}
