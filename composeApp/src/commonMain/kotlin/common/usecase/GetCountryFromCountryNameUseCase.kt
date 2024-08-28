package common.usecase

import common.model.Country
import futibasrotterdam.composeapp.generated.resources.Res
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.ExperimentalResourceApi

@OptIn(ExperimentalResourceApi::class)
class GetCountryFromCountryNameUseCase {

    suspend operator fun invoke(countryName: String): Country? {
        val countriesJson = Res.readBytes("files/Countries.json").decodeToString()
        val countries = Json.decodeFromString<List<Country>>(countriesJson)

        return countries.find {
            it.name == countryName
        }
    }
}
