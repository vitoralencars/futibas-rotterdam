package profile.presentation.menu.newgamelist

import profile.domain.model.Court

sealed interface NewGameListScreenState {
    data object Loading : NewGameListScreenState

    data class Content(
        val gameId: String? = null,
        val date: String = "18:00",
        val time: String = "",
        val location: String = "",
        val maxPlayers: Int = 20,
        val courts: List<Court> = emptyList(),
        val shouldShowDatePicker: Boolean = false,
        val shouldShowTimePicker: Boolean = false,
        val isConfiguringList: Boolean = false,
        val canCreateList: Boolean = false,
    ) : NewGameListScreenState

    fun asContent() = this as? Content
}
