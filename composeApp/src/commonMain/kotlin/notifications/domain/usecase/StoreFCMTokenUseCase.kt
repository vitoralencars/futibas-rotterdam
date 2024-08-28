package notifications.domain.usecase

import notifications.domain.repository.NotificationsRepository

class StoreFCMTokenUseCase(
    private val notificationsRepository: NotificationsRepository,
) {

    suspend operator fun invoke(fcmToken: String) {
        notificationsRepository.storeFCMToken(fcmToken)
    }
}
