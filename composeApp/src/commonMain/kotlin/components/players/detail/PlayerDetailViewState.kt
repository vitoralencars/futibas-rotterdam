package components.players.detail

import player.domain.model.Player

data class PlayerDetailViewState(
    val player: Player,
    val shouldShowHierarchy: Boolean = true,
)
