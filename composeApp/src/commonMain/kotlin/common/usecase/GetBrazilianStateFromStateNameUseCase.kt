package common.usecase

import common.model.BrazilianState
import futibasrotterdam.composeapp.generated.resources.Res
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.ExperimentalResourceApi

@OptIn(ExperimentalResourceApi::class)
class GetBrazilianStateFromStateNameUseCase {
    suspend operator fun invoke(stateName: String): BrazilianState? {
        val statesJson = Res.readBytes("files/BrazilianStates.json").decodeToString()
        val brazilianStates = Json.decodeFromString<List<BrazilianState>>(statesJson)

        return brazilianStates.find {
            it.name == stateName
        }
    }
}
