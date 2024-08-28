package registration.data.repository

import common.model.BrazilianState
import common.model.Country
import futibasrotterdam.composeapp.generated.resources.Res
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.ExperimentalResourceApi
import registration.domain.repository.LocationRepository

@OptIn(ExperimentalResourceApi::class)
class LocationRepositoryImpl : LocationRepository {

    override suspend fun getCountries(): List<Country> {
        val countriesJson = Res.readBytes("files/Countries.json").decodeToString()
        return Json.decodeFromString<List<Country>>(countriesJson)
    }

    override suspend fun getBrazilianStates(): List<BrazilianState> {
        val statesJson = Res.readBytes("files/BrazilianStates.json").decodeToString()
        return Json.decodeFromString<List<BrazilianState>>(statesJson)
    }
}
