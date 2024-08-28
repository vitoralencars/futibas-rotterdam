package registration.domain.model

import kotlinx.serialization.Serializable
import registration.domain.model.PlayerDataRegister

@Serializable
data class PlayerRegisterRequest(
    val playerDataRegister: PlayerDataRegister,
)
