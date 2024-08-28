package components.button

sealed interface ButtonViewState {

    data object Loading : ButtonViewState

    data class Content(
        val text: String,
        val isEnabled: Boolean = true,
    ) : ButtonViewState
}
