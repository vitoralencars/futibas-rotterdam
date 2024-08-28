package notifications.data.service

import common.model.StringApiResponse
import network.ServiceHandler
import network.ServiceResult
import notifications.domain.model.NewFCMToken

class NotificationsServiceImpl(
    private val serviceHandler: ServiceHandler,
) : NotificationsService {

    override suspend fun updateFCMToken(fcmToken: NewFCMToken): ServiceResult<StringApiResponse> {
        return serviceHandler.performServiceCall(
            url = "https://ciaki9j632.execute-api.sa-east-1.amazonaws.com/default/updateFutibasPlayerFCMToken",
            body = fcmToken,
        )
    }
}
