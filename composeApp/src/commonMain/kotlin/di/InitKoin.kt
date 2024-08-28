package di

import common.di.commonModule
import gamelist.di.gameListModule
import login.di.loginModule
import main.di.mainModule
import network.di.networkModule
import notifications.di.notificationsModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import platform.di.platformModule
import player.di.playerModule
import playerslist.di.playersListModule
import profile.di.profileModule
import registration.di.registrationModule
import splash.di.splashModule

fun initKoin(config: KoinAppDeclaration? = null) {

    startKoin {
        config?.invoke(this)
        modules(
            mainModule,
            platformModule,
            networkModule,
            commonModule,
            splashModule,
            loginModule,
            notificationsModule,
            registrationModule,
            playerModule,
            playersListModule,
            gameListModule,
            profileModule,
        )
    }
}
