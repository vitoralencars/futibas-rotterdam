package com.rotterdam.futibas

import android.app.Application
import android.content.Context
import di.initKoin
import org.koin.android.ext.koin.androidContext

class FutibasApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        CONTEXT = this.applicationContext
        initKoin {
            androidContext(this@FutibasApplication)
        }
    }

    companion object {
        lateinit var CONTEXT: Context
    }
}
