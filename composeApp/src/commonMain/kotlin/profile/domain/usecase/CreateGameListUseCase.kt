package profile.domain.usecase

import profile.domain.model.newgamelist.NewGameList
import profile.domain.repository.ProfileRepository

class CreateGameListUseCase(
    private val repository: ProfileRepository,
) {
    suspend operator fun invoke(newGameList: NewGameList) = repository.createGameList(
        newGameList = newGameList,
    )
}
