package player.data.service

import common.model.StringApiResponse
import network.ServiceHandler
import network.ServiceResult
import player.data.model.PlayersDataList
import player.domain.model.NewPlayerLevel
import player.domain.model.PlayersIds

class PlayerServiceImpl(
    private val serviceHandler: ServiceHandler,
) : PlayerService {

    override suspend fun fetchPlayersList(playersIds: PlayersIds): ServiceResult<PlayersDataList> {
        return serviceHandler.performServiceCall(
            url = "https://iutaukp419.execute-api.sa-east-1.amazonaws.com/default/getFutibasPlayersByIds",
            body = playersIds,
        )
    }

    override suspend fun updatePlayerLevel(
        newPlayerLevel: NewPlayerLevel,
    ): ServiceResult<StringApiResponse> {
        return serviceHandler.performServiceCall(
            url = "https://6y8k4l4741.execute-api.sa-east-1.amazonaws.com/default/updatePlayerLevel",
            body = newPlayerLevel,
        )
    }
}
