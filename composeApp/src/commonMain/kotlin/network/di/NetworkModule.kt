package network.di

import io.ktor.client.HttpClient
import network.ServiceHandler
import org.koin.dsl.module
import platform.network.createHttpClient

val networkModule = module {
    single<HttpClient> {
        createHttpClient()
    }
    single<ServiceHandler> { ServiceHandler(get()) }
}
