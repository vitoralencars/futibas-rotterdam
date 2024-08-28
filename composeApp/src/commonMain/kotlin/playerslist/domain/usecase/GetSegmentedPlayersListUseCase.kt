package playerslist.domain.usecase

import player.domain.model.Player
import player.domain.model.PlayerLevel

class GetSegmentedPlayersListUseCase {

    operator fun invoke(playersList: List<Player>, playerLevel: PlayerLevel): List<Player> {
        return playersList.filter {
            it.level == playerLevel
        }
    }
}
