package components.players.list

import player.domain.model.Player

data class PlayersListViewState(
    val players: List<Player>,
    val showHierarchyLevel: Boolean = false,
    val photoColorSaturation: Float = 1F,
)
