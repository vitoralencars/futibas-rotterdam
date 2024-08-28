package profile.presentation.menu.pendingplayers

import player.domain.model.Player

data class PlayerItemViewState(
    val player: Player,
    val actionStatus: ActionStatus = ActionStatus.None
)

sealed interface ActionStatus {
    data object Approving : ActionStatus
    data object Denying : ActionStatus
    data object None : ActionStatus
}
