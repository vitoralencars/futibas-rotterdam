package login.domain.usecase

import player.domain.model.LoggedInPlayer
import login.domain.model.LoginPlayerRequest
import login.domain.repository.LoginRepository
import network.ServiceResult

class LoginPlayerUseCase(private val repository: LoginRepository) {
    suspend operator fun invoke(
        logInPlayerRequest: LoginPlayerRequest,
    ): ServiceResult<LoggedInPlayer?> {
        return repository.loginPlayer(logInPlayerRequest)
    }
}
