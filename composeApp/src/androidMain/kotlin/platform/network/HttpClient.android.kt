package platform.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import network.configureHttpClient

actual fun createHttpClient(): HttpClient = configureHttpClient(OkHttp)
