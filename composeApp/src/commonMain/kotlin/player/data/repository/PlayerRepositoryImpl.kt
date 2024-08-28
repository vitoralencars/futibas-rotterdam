package player.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import common.util.PrefsKeys.LOGGED_IN_PLAYER_KEY
import kotlinx.coroutines.flow.first
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import network.ServiceResult
import player.data.mapper.PlayerDataToPlayerDomainMapper
import player.domain.model.PlayersIds
import player.data.service.PlayerService
import player.domain.model.LoggedInPlayer
import player.domain.model.NewPlayerLevel
import player.domain.model.Player
import player.domain.repository.PlayerRepository

class PlayerRepositoryImpl(
    private val playerService: PlayerService,
    private val dataStore: DataStore<Preferences>,
    private val playerDataToPlayerDomainMapper: PlayerDataToPlayerDomainMapper,
) : PlayerRepository {

    override suspend fun storeLoggedInPlayer(
        player: LoggedInPlayer,
    ) {
        dataStore.edit { prefs ->
            prefs[LOGGED_IN_PLAYER_KEY] = Json.encodeToString(player)
        }
    }

    override suspend fun retrieveLoggedInPlayer(): LoggedInPlayer? {
        val storedData = dataStore.data.first()[LOGGED_IN_PLAYER_KEY]

        return storedData?.let { data ->
            Json.decodeFromString(data)
        }
    }

    override suspend fun fetchPlayersList(playersIds: PlayersIds): ServiceResult<List<Player>> {
        return when(val serviceResult = playerService.fetchPlayersList(playersIds)) {
            is ServiceResult.Success -> {
                val domainPlayersList = serviceResult.response.players.map {
                    playerDataToPlayerDomainMapper(it)
                }
                ServiceResult.Success(domainPlayersList)
            }
            is ServiceResult.Error -> serviceResult
        }
    }

    override suspend fun updatePlayerLevel(newPlayerLevel: NewPlayerLevel): ServiceResult<Any?> {
        return playerService.updatePlayerLevel(newPlayerLevel)
    }
}
