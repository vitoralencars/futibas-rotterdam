package registration.domain.usecase

import player.domain.model.LoggedInPlayer
import network.ServiceResult
import registration.domain.model.PlayerDataRegister
import registration.domain.repository.RegistrationRepository

class RegisterPlayerUseCase(
    private val repository: RegistrationRepository
) {

    suspend operator fun invoke(
        playerDataRegister: PlayerDataRegister,
    ): ServiceResult<LoggedInPlayer> {
        return repository.registerPlayer(playerDataRegister = playerDataRegister)
    }
}
