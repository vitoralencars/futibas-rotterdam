package registration.domain.repository

import player.domain.model.LoggedInPlayer
import network.ServiceResult
import registration.domain.model.PlayerDataRegister

interface RegistrationRepository {

    suspend fun registerPlayer(
        playerDataRegister: PlayerDataRegister
    ): ServiceResult<LoggedInPlayer>
}
