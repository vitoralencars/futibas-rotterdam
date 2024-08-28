package player.domain.repository

import network.ServiceResult
import player.domain.model.PlayersIds
import player.domain.model.LoggedInPlayer
import player.domain.model.NewPlayerLevel
import player.domain.model.Player

interface PlayerRepository {

    suspend fun storeLoggedInPlayer(player: LoggedInPlayer)

    suspend fun retrieveLoggedInPlayer(): LoggedInPlayer?

    suspend fun fetchPlayersList(playersIds: PlayersIds): ServiceResult<List<Player>>

    suspend fun updatePlayerLevel(newPlayerLevel: NewPlayerLevel): ServiceResult<Any?>
}
