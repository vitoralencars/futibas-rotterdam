package platform.network

import io.ktor.client.HttpClient

expect fun createHttpClient(): HttpClient
