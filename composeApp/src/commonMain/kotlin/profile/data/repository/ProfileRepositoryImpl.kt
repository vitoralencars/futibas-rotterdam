package profile.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import common.util.PrefsKeys.LOGGED_IN_PLAYER_KEY
import futibasrotterdam.composeapp.generated.resources.Res
import kotlinx.serialization.json.Json
import network.ServiceResult
import org.jetbrains.compose.resources.ExperimentalResourceApi
import player.data.mapper.PlayerDataToPlayerDomainMapper
import player.domain.model.LoggedInPlayer
import player.domain.model.Player
import profile.data.mapper.CourtDataToDomainMapper
import profile.data.model.CourtData
import profile.data.service.ProfileService
import profile.domain.model.Court
import profile.domain.model.newgamelist.NewGameList
import profile.data.model.NewGameListRequest
import profile.data.model.request.DeletePlayerAccountRequest
import profile.data.model.request.UpdateGameListRequest
import profile.domain.model.playerdata.UpdatePlayerDataRequest
import profile.domain.model.playerdata.UploadPlayerPhotoRequest
import profile.domain.repository.ProfileRepository

class ProfileRepositoryImpl(
    private val service: ProfileService,
    private val courtDataToDomainMapper: CourtDataToDomainMapper,
    private val playerDataToPlayerDomainMapper: PlayerDataToPlayerDomainMapper,
    private val dataStore: DataStore<Preferences>,
) : ProfileRepository {

    override suspend fun updatePlayerData(
        updatePlayerDataRequest: UpdatePlayerDataRequest,
    ): ServiceResult<LoggedInPlayer> {

        return when (val result = service.updatePlayerData(updatePlayerDataRequest)) {
            is ServiceResult.Success -> {
                result.response?.loggedInPlayer?.let { player ->
                    ServiceResult.Success(
                        response = player,
                    )
                } ?: ServiceResult.Error(exception = Throwable(
                    message = "Erro ao atualizar dados do  jogador",
                ))
            }
            is ServiceResult.Error -> result
        }
    }

    override suspend fun uploadPlayerPhoto(
        uploadPlayerPhotoRequest: UploadPlayerPhotoRequest
    ): ServiceResult<String> {
        return when (val result = service.uploadPlayerPhoto(uploadPlayerPhotoRequest)) {
            is ServiceResult.Success -> {
                ServiceResult.Success(
                    response = result.response.photoUrl
                )
            }
            is ServiceResult.Error -> result
        }
    }

    override suspend fun createGameList(
        newGameList: NewGameList
    ): ServiceResult<Any?> {
        return service.createGameList(NewGameListRequest(newGameList))
    }

    @OptIn(ExperimentalResourceApi::class)
    override suspend fun getCourts(): List<Court> {
        val courtsJson = Res.readBytes("files/Courts.json").decodeToString()
        val courtsData = Json.decodeFromString<List<CourtData>>(courtsJson)

        return courtsData.map {
            courtDataToDomainMapper(it)
        }
    }

    override suspend fun fetchPendingPlayers(): ServiceResult<List<Player>> {
        return when(val serviceResult = service.fetchPendingPlayers()) {
            is ServiceResult.Success -> {
                val domainPlayersList = serviceResult.response.players.map {
                    playerDataToPlayerDomainMapper(it)
                }
                ServiceResult.Success(domainPlayersList)
            }
            is ServiceResult.Error -> serviceResult
        }
    }

    override suspend fun updateGameList(
        updateGameListRequest: UpdateGameListRequest,
    ): ServiceResult<Any?> {
        return service.updateGameList(updateGameListRequest)
    }

    override suspend fun removeLoggedInPlayer() {
        dataStore.edit { prefs ->
            prefs.remove(LOGGED_IN_PLAYER_KEY)
        }
    }

    override suspend fun deletePlayerAccount(
        deletePlayerAccountRequest: DeletePlayerAccountRequest,
    ): ServiceResult<Any?> {
        return service.deletePlayerAccount(deletePlayerAccountRequest)
    }
}
