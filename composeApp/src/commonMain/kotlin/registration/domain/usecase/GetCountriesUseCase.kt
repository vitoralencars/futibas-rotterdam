package registration.domain.usecase

import registration.domain.repository.LocationRepository

class GetCountriesUseCase(
    private val repository: LocationRepository
) {
    suspend operator fun invoke() = repository.getCountries()
}
