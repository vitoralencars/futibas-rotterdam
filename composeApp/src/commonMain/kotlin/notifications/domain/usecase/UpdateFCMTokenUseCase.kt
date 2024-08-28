package notifications.domain.usecase

import notifications.domain.model.NewFCMToken
import notifications.domain.repository.NotificationsRepository
import player.domain.repository.PlayerRepository

class UpdateFCMTokenUseCase(
    private val notificationsRepository: NotificationsRepository,
    private val playerRepository: PlayerRepository,
) {

    suspend operator fun invoke() {
        val loggedInPlayer = playerRepository.retrieveLoggedInPlayer()
        val storedFCMToken = notificationsRepository.retrieveFCMToken()
        loggedInPlayer?.let { player ->
            if (player.fcmToken != storedFCMToken) {
                notificationsRepository.updateFCMToken(
                    fcmToken = NewFCMToken(
                        playerId = player.playerId,
                        fcmToken = storedFCMToken,
                    )
                )
            }
        }
    }
}
