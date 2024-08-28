package profile.data.service

import common.model.StringApiResponse
import network.ServiceResult
import player.data.model.PlayersDataList
import profile.data.model.NewGameListRequest
import profile.data.model.LoggedInPlayerData
import profile.data.model.PlayerPhotoData
import profile.data.model.request.DeletePlayerAccountRequest
import profile.data.model.request.UpdateGameListRequest
import profile.domain.model.playerdata.UpdatePlayerDataRequest
import profile.domain.model.playerdata.UploadPlayerPhotoRequest

interface ProfileService {

    suspend fun updatePlayerData(
        playerDataRequest: UpdatePlayerDataRequest,
    ): ServiceResult<LoggedInPlayerData?>

    suspend fun uploadPlayerPhoto(
        uploadPlayerPhotoRequest: UploadPlayerPhotoRequest,
    ): ServiceResult<PlayerPhotoData>

    suspend fun createGameList(
        newGameListRequest: NewGameListRequest
    ): ServiceResult<StringApiResponse>

    suspend fun fetchPendingPlayers(): ServiceResult<PlayersDataList>

    suspend fun updateGameList(
        updateGameListRequest: UpdateGameListRequest
    ): ServiceResult<StringApiResponse>

    suspend fun deletePlayerAccount(
        deletePlayerAccountRequest: DeletePlayerAccountRequest,
    ): ServiceResult<StringApiResponse>
}
