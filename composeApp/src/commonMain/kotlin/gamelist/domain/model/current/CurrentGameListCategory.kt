package gamelist.domain.model.current

import player.domain.model.Player

data class CurrentGameListCategory(
    val gameListType: CurrentGameListType,
    val players: List<Player>,
)
