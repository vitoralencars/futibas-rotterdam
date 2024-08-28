package registration.domain.repository

import common.model.BrazilianState
import common.model.Country

interface LocationRepository {

    suspend fun getCountries(): List<Country>

    suspend fun getBrazilianStates(): List<BrazilianState>
}
