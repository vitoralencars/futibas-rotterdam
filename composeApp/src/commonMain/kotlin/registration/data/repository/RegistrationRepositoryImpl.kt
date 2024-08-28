package registration.data.repository

import player.domain.model.LoggedInPlayer
import network.ServiceResult
import registration.data.service.RegistrationService
import registration.domain.model.PlayerDataRegister
import registration.domain.model.PlayerRegisterRequest
import registration.domain.repository.RegistrationRepository

class RegistrationRepositoryImpl(
    private val registrationService: RegistrationService,
) : RegistrationRepository {

    override suspend fun registerPlayer(
        playerDataRegister: PlayerDataRegister
    ) : ServiceResult<LoggedInPlayer> {
        val result = registrationService.registerPlayer(
            playerRegisterRequest = PlayerRegisterRequest(
                playerDataRegister = playerDataRegister,
            )
        )

        return when (result) {
            is ServiceResult.Success -> {
                result.response?.loggedInPlayer?.let { player ->
                    ServiceResult.Success(
                        response = player
                    )
                } ?: ServiceResult.Error(exception = Throwable(message = "Erro ao buscar jogador"))
            }
            is ServiceResult.Error -> result
        }
    }
}
