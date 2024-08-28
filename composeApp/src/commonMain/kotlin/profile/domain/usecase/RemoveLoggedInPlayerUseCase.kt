package profile.domain.usecase

import profile.domain.repository.ProfileRepository

class RemoveLoggedInPlayerUseCase(private val repository: ProfileRepository) {

    suspend operator fun invoke() {
        repository.removeLoggedInPlayer()
    }
}
