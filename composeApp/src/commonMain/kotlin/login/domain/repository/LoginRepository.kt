package login.domain.repository

import player.domain.model.LoggedInPlayer
import login.domain.model.LoginPlayerRequest
import network.ServiceResult

interface LoginRepository {

    suspend fun loginPlayer(loginPlayerRequest: LoginPlayerRequest): ServiceResult<LoggedInPlayer?>
}
