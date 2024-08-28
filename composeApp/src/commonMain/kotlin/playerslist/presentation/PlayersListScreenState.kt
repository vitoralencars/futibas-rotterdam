package playerslist.presentation

import player.domain.model.Player

sealed interface PlayersListScreenState {
    val isRefreshing: Boolean

    data class Error(
        override val isRefreshing: Boolean = false,
    ): PlayersListScreenState

    data class Loading(
        override val isRefreshing: Boolean = false,
    ): PlayersListScreenState

    data class Content(
        override val isRefreshing: Boolean = false,
        val presidentList: List<Player>,
        val boardList: List<Player>,
        val monthlyList: List<Player>,
        val spotList: List<Player>,
    ): PlayersListScreenState

    fun setRefreshing(
        isRefreshing: Boolean
    ): PlayersListScreenState {
        return when (this) {
            is Loading -> this.copy(isRefreshing = isRefreshing)
            is Error -> this.copy(isRefreshing = isRefreshing)
            is Content -> this.copy(isRefreshing = isRefreshing)
        }
    }
}
