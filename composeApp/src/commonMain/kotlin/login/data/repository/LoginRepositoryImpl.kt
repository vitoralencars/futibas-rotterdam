package login.data.repository

import player.domain.model.LoggedInPlayer
import login.data.service.LoginService
import login.domain.model.LoginPlayerRequest
import login.domain.repository.LoginRepository
import network.ServiceResult
import notifications.domain.usecase.UpdateFCMTokenUseCase
import player.domain.usecase.StoreLoggedInPlayerUseCase

class LoginRepositoryImpl(
    private val service: LoginService,
    private val storeLoggedInPlayerUseCase: StoreLoggedInPlayerUseCase,
    private val updateFCMTokenUseCase: UpdateFCMTokenUseCase,
) : LoginRepository {

    override suspend fun loginPlayer(
        loginPlayerRequest: LoginPlayerRequest,
    ): ServiceResult<LoggedInPlayer?> {

        return when (val result = service.loginPlayer(loginPlayerRequest)) {
            is ServiceResult.Success -> {
                result.response?.loggedInPlayer?.let {
                    storeLoggedInPlayerUseCase(it)
                    updateFCMTokenUseCase()
                }
                ServiceResult.Success(
                    response = result.response?.loggedInPlayer
                )
            }
            is ServiceResult.Error -> ServiceResult.Error(
                Throwable(message = result.exception.message)
            )
        }
    }
}
