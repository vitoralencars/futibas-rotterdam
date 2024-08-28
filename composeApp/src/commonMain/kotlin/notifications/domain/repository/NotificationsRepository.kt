package notifications.domain.repository

import network.ServiceResult
import notifications.domain.model.NewFCMToken

interface NotificationsRepository {

    suspend fun storeFCMToken(fcmToken: String)

    suspend fun retrieveFCMToken(): String

    suspend fun updateFCMToken(fcmToken: NewFCMToken): ServiceResult<Any?>
}
