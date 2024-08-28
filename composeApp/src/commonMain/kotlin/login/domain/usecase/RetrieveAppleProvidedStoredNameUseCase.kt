package login.domain.usecase

import login.domain.repository.LoginRepository

class RetrieveAppleProvidedStoredNameUseCase(
    private val repository: LoginRepository,
) {
    suspend operator fun invoke(): String {
        return repository.retrieveAppleProvidedStoredName()
    }
}
