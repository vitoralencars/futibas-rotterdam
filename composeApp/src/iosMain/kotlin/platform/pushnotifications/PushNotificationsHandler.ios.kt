package platform.pushnotifications

import cocoapods.FirebaseMessaging.FIRMessaging
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import notifications.domain.usecase.StoreFCMTokenUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@OptIn(ExperimentalForeignApi::class)
actual fun subscribeToFCMTopic(topic: String) {
    FIRMessaging.messaging().subscribeToTopic(topic)
}

actual fun storeFCMToken(fcmToken: String) {
    CoroutineScope(Dispatchers.Main).launch {
        KoinHelper.storeFCMTokenUseCase(fcmToken)
    }
}

private object KoinHelper : KoinComponent {
    val storeFCMTokenUseCase: StoreFCMTokenUseCase by inject()
}
