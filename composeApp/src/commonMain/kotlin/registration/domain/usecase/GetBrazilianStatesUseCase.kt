package registration.domain.usecase

import registration.domain.repository.LocationRepository

class GetBrazilianStatesUseCase(
    private val repository: LocationRepository
) {
    suspend operator fun invoke() = repository.getBrazilianStates()
}
