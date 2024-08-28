package notifications.data.service

import common.model.StringApiResponse
import network.ServiceResult
import notifications.domain.model.NewFCMToken

interface NotificationsService {

    suspend fun updateFCMToken(fcmToken: NewFCMToken): ServiceResult<StringApiResponse>
}
