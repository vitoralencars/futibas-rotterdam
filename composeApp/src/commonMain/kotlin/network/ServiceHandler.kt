package network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode

class ServiceHandler (
    val httpClient: HttpClient,
) {
    suspend inline fun <reified T> performServiceCall(
        url: String,
        body: Any? = null,
    ): ServiceResult<T> {
        return try {
            val response = httpClient.post(url) {
                url(url)
                setBody(body)
            }

            if (response.status == HttpStatusCode.OK) {
                val apiResponse: ApiResponse<T> = response.body()
                ServiceResult.Success(apiResponse.body)
            } else {
                ServiceResult.Error(exception = Exception(response.bodyAsText()))
            }
        } catch (e: Exception) {
            ServiceResult.Error(exception = e)
        }
    }
}
