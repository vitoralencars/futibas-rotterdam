package common.model

import kotlinx.serialization.Serializable

@Serializable
data class StringApiResponse(
    val message: String,
)
