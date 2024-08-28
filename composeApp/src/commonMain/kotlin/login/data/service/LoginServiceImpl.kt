package login.data.service

import login.domain.model.LoginPlayerRequest
import network.ServiceHandler
import network.ServiceResult
import profile.data.model.LoggedInPlayerData

class LoginServiceImpl(
    private val serviceHandler: ServiceHandler,
) : LoginService {

    override suspend fun loginPlayer(
        loginPlayerRequest: LoginPlayerRequest,
    ): ServiceResult<LoggedInPlayerData?> {
        return serviceHandler.performServiceCall(
            url = "https://6qz04q0538.execute-api.sa-east-1.amazonaws.com/default/getFutibasPlayer",
            body = loginPlayerRequest,
        )
    }
}
