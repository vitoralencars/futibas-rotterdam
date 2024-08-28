package login.data.service

import login.domain.model.LoginPlayerRequest
import network.ServiceResult
import profile.data.model.LoggedInPlayerData

interface LoginService {

    suspend fun loginPlayer(
        loginPlayerRequest: LoginPlayerRequest,
    ): ServiceResult<LoggedInPlayerData?>
}
