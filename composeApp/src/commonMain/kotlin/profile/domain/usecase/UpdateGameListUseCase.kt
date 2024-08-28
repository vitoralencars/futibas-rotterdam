package profile.domain.usecase

import network.ServiceResult
import profile.data.model.request.UpdateGameListRequest
import profile.domain.repository.ProfileRepository

class UpdateGameListUseCase(
    private val repository: ProfileRepository,
) {
    suspend operator fun invoke(
        updateGameListRequest: UpdateGameListRequest,
    ): ServiceResult<Any?> {
        return repository.updateGameList(updateGameListRequest)
    }
}
