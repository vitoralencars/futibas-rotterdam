package profile.data.mapper

import futibasrotterdam.composeapp.generated.resources.Res
import futibasrotterdam.composeapp.generated.resources.delfshaven
import futibasrotterdam.composeapp.generated.resources.sporthal_snelleman
import profile.data.model.CourtData
import profile.domain.model.Court

class CourtDataToDomainMapper {
    operator fun invoke(data: CourtData): Court {
        val imageRes = when (data.imageName) {
            "sporthal_snelleman" -> Res.drawable.sporthal_snelleman
            "delfshaven" -> Res.drawable.delfshaven
            else -> Res.drawable.sporthal_snelleman
        }

        return with(data) {
            Court(
                name = name,
                address = address,
                mapsUrl = mapsUrl,
                imageRes = imageRes,
            )
        }
    }
}
