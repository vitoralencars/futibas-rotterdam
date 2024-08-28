package common.model

import kotlinx.serialization.Serializable

@Serializable
data class Country(
    val name: String,
    val flag: String,
)
