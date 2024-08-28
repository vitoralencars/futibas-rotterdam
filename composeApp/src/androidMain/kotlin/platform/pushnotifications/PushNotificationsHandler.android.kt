package platform.pushnotifications

import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import notifications.domain.usecase.StoreFCMTokenUseCase
import org.koin.core.context.GlobalContext

actual fun subscribeToFCMTopic(topic: String) {
    FirebaseMessaging.getInstance().subscribeToTopic(topic).addOnCompleteListener { task ->
        if (task.isSuccessful) {
            val token = FirebaseMessaging.getInstance().token
        }
    }
}

actual fun storeFCMToken(fcmToken: String) {
    val storeFCMTokenUseCase: StoreFCMTokenUseCase = GlobalContext.get().get()
    CoroutineScope(Dispatchers.Main).launch {
        storeFCMTokenUseCase(fcmToken)
    }
}
