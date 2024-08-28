package player.data.service

import common.model.StringApiResponse
import network.ServiceResult
import player.data.model.PlayersDataList
import player.domain.model.PlayersIds
import player.domain.model.NewPlayerLevel

interface PlayerService {

    suspend fun fetchPlayersList(playersIds: PlayersIds) : ServiceResult<PlayersDataList>

    suspend fun updatePlayerLevel(
        newPlayerLevel: NewPlayerLevel,
    ) : ServiceResult<StringApiResponse>
}
