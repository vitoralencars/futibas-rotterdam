package com.rotterdam.futibas

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import platform.pushnotifications.storeFCMToken

class PushNotificationsService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
    }

    override fun onNewToken(token: String) {
        storeFCMToken(token)
        super.onNewToken(token)
    }
}
