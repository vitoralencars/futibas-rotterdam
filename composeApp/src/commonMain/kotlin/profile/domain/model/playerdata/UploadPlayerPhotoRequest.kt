package profile.domain.model.playerdata

import kotlinx.serialization.Serializable

@Serializable
data class UploadPlayerPhotoRequest(
    val playerId: String,
    val imageData: String,
)
