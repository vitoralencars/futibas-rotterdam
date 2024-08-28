package registration.data.service

import network.ServiceResult
import profile.data.model.LoggedInPlayerData
import registration.domain.model.PlayerRegisterRequest

interface RegistrationService {
    suspend fun registerPlayer(
        playerRegisterRequest: PlayerRegisterRequest,
    ): ServiceResult<LoggedInPlayerData?>
}
