package profile.domain.model

import org.jetbrains.compose.resources.DrawableResource

data class Court(
    val name: String,
    val mapsUrl: String,
    val address: String,
    val imageRes: DrawableResource,
)
