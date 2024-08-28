package platform.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin
import network.configureHttpClient

actual fun createHttpClient(): HttpClient = configureHttpClient(Darwin)
