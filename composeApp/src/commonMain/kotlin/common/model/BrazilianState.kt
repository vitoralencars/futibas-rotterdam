package common.model

import kotlinx.serialization.Serializable

@Serializable
data class BrazilianState(
    val name: String,
    val abbreviation: String,
)
