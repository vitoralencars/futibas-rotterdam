package profile.domain.usecase

import profile.domain.model.Court
import profile.domain.repository.ProfileRepository

class GetCourtsUseCase(
    private val repository: ProfileRepository,
) {
    suspend operator fun invoke(): List<Court> = repository.getCourts()
}
