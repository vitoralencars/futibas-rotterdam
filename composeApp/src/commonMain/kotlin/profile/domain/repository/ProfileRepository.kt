package profile.domain.repository

import network.ServiceResult
import player.domain.model.LoggedInPlayer
import player.domain.model.Player
import profile.data.model.request.DeletePlayerAccountRequest
import profile.data.model.request.UpdateGameListRequest
import profile.domain.model.Court
import profile.domain.model.newgamelist.NewGameList
import profile.domain.model.playerdata.UpdatePlayerDataRequest
import profile.domain.model.playerdata.UploadPlayerPhotoRequest

interface ProfileRepository {

    suspend fun updatePlayerData(
        updatePlayerDataRequest: UpdatePlayerDataRequest,
    ): ServiceResult<LoggedInPlayer>

    suspend fun uploadPlayerPhoto(
        uploadPlayerPhotoRequest: UploadPlayerPhotoRequest,
    ): ServiceResult<String>

    suspend fun createGameList(
        newGameList: NewGameList,
    ): ServiceResult<Any?>

    suspend fun getCourts(): List<Court>

    suspend fun fetchPendingPlayers(): ServiceResult<List<Player>>

    suspend fun updateGameList(
        updateGameListRequest: UpdateGameListRequest,
    ): ServiceResult<Any?>

    suspend fun removeLoggedInPlayer()

    suspend fun deletePlayerAccount(
        deletePlayerAccountRequest: DeletePlayerAccountRequest,
    ): ServiceResult<Any?>
}
