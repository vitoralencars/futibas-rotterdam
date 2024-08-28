package profile.presentation.menu.pendingplayers

sealed interface PendingPlayersScreenState {
    val isRefreshing: Boolean

    data class Loading(
        override val isRefreshing: Boolean = false,
    ) : PendingPlayersScreenState
    data class Empty(
        override val isRefreshing: Boolean = false,
    ) : PendingPlayersScreenState
    data class Content(
        val playersViewStates: List<PlayerItemViewState>,
        override val isRefreshing: Boolean = false,
    ) : PendingPlayersScreenState

    fun asContent() = this as? Content

    fun setRefreshing(
        isRefreshing: Boolean
    ): PendingPlayersScreenState {
        return when (this) {
            is Loading -> this.copy(isRefreshing = isRefreshing)
            is Empty -> this.copy(isRefreshing = isRefreshing)
            is Content -> this.copy(isRefreshing = isRefreshing)
        }
    }
}
