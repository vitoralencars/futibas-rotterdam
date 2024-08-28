package profile.data.model

import kotlinx.serialization.Serializable

@Serializable
data class CourtData(
    val name: String,
    val mapsUrl: String,
    val address: String,
    val imageName: String,
)
