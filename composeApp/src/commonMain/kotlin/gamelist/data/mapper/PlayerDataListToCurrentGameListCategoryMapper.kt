package gamelist.data.mapper

import gamelist.domain.model.current.CurrentGameListCategory
import gamelist.domain.model.current.CurrentGameListType
import player.data.mapper.PlayerDataToPlayerDomainMapper
import player.data.model.PlayerData

class PlayerDataListToCurrentGameListCategoryMapper(
    private val playerMapper: PlayerDataToPlayerDomainMapper,
) {
    suspend operator fun invoke(params: Params): CurrentGameListCategory {
        val players = params.players.map {
            playerMapper(it)
        }

        return CurrentGameListCategory(
            gameListType = params.currentGameListType,
            players = players,
        )
    }

    data class Params(
        val currentGameListType: CurrentGameListType,
        val players: List<PlayerData>,
    )
}
