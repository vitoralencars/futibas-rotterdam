package notifications.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class NewFCMToken(
    private val playerId: String,
    private val fcmToken: String,
)
