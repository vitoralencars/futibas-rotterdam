package network

sealed interface ServiceResult<out T> {
    data class Success<out T>(val response: T): ServiceResult<T>
    data class Error(val exception: Throwable): ServiceResult<Nothing>
}
