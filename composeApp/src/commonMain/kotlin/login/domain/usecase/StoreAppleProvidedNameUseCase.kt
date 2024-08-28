package login.domain.usecase

import login.domain.repository.LoginRepository

class StoreAppleProvidedNameUseCase(
    val repository: LoginRepository,
) {
    suspend operator fun invoke(name: String) {
        repository.storeAppleProvidedName(name)
    }
}
