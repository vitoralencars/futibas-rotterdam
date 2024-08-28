package profile.data.service

import common.model.StringApiResponse
import network.ServiceHandler
import network.ServiceResult
import player.data.model.PlayersDataList
import profile.data.model.NewGameListRequest
import profile.data.model.LoggedInPlayerData
import profile.data.model.PlayerPhotoData
import profile.data.model.request.DeletePlayerAccountRequest
import profile.data.model.request.UpdateGameListRequest
import profile.domain.model.playerdata.UpdatePlayerDataRequest
import profile.domain.model.playerdata.UploadPlayerPhotoRequest

class ProfileServiceImpl(
    private val serviceHandler: ServiceHandler,
) : ProfileService {

    override suspend fun updatePlayerData(
        playerDataRequest: UpdatePlayerDataRequest,
    ): ServiceResult<LoggedInPlayerData?> {
        return serviceHandler.performServiceCall(
            url = "https://vts561qasl.execute-api.sa-east-1.amazonaws.com/default/updateFutibasPlayer",
            body = playerDataRequest,
        )
    }

    override suspend fun uploadPlayerPhoto(
        uploadPlayerPhotoRequest: UploadPlayerPhotoRequest,
    ): ServiceResult<PlayerPhotoData> {
        return serviceHandler.performServiceCall(
            url = "https://u5j4le0ywi.execute-api.sa-east-1.amazonaws.com/default/uploadFutibasPlayerPhoto",
            body = uploadPlayerPhotoRequest,
        )
    }

    override suspend fun createGameList(
        newGameListRequest: NewGameListRequest
    ): ServiceResult<StringApiResponse> {
        return serviceHandler.performServiceCall(
            url = "https://n64h6olr63.execute-api.sa-east-1.amazonaws.com/default/createFutibasGameList",
            body = newGameListRequest,
        )
    }

    override suspend fun fetchPendingPlayers(): ServiceResult<PlayersDataList> {
        return serviceHandler.performServiceCall(
            url = "https://0dfw1skrd3.execute-api.sa-east-1.amazonaws.com/default/getFutibasPendingPlayers",
        )
    }

    override suspend fun updateGameList(
        updateGameListRequest: UpdateGameListRequest,
    ): ServiceResult<StringApiResponse> {
        return serviceHandler.performServiceCall(
            url = "https://hf0tkngq2i.execute-api.sa-east-1.amazonaws.com/default/updateFutibasGameList",
            body = updateGameListRequest,
        )
    }

    override suspend fun deletePlayerAccount(
        deletePlayerAccountRequest: DeletePlayerAccountRequest,
    ): ServiceResult<StringApiResponse> {
        return serviceHandler.performServiceCall(
            url = "https://6kov84yvbl.execute-api.sa-east-1.amazonaws.com/default/deleteFutibasPlayer",
            body = deletePlayerAccountRequest,
        )
    }
}
