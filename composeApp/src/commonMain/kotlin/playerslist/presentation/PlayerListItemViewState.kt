package playerslist.presentation

import player.domain.model.Player

data class PlayerListItemViewState(
    val hierarchy: String,
    val players: List<Player>,
)
