package com.rotterdam.futibas

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import di.initKoin
import org.koin.android.ext.koin.androidContext

class FutibasApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@FutibasApplication)
        }
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        val channelId = "default_channel"
        val channelName = "Default Notifications"
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
        notificationManager.createNotificationChannel(channel)
    }
}
