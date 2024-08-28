package profile.domain.usecase

import network.ServiceResult
import player.domain.model.LoggedInPlayer
import profile.domain.model.playerdata.UpdatePlayerDataRequest
import profile.domain.repository.ProfileRepository

class UpdatePlayerDataUseCase(
    private val repository: ProfileRepository,
) {

    suspend operator fun invoke(
        updatePlayerDataRequest: UpdatePlayerDataRequest
    ): ServiceResult<LoggedInPlayer> = repository.updatePlayerData(updatePlayerDataRequest)
}
