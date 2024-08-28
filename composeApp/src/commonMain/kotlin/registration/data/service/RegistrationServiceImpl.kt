package registration.data.service

import network.ServiceHandler
import network.ServiceResult
import profile.data.model.LoggedInPlayerData
import registration.domain.model.PlayerRegisterRequest

class RegistrationServiceImpl(
    private val serviceHandler: ServiceHandler,
) : RegistrationService {

    override suspend fun registerPlayer(
        playerRegisterRequest: PlayerRegisterRequest,
    ): ServiceResult<LoggedInPlayerData?> {
        return serviceHandler.performServiceCall(
            url = "https://4k114drz23.execute-api.sa-east-1.amazonaws.com/default/createFutibasPlayer",
            body = playerRegisterRequest,
        )
    }
}
