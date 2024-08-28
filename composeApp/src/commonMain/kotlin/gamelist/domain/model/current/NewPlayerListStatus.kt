package gamelist.domain.model.current

import player.domain.model.LoggedInPlayer

data class NewPlayerListStatus(
    val status: GameListPlayerStatus,
    val loggedInPlayer: LoggedInPlayer,
)
