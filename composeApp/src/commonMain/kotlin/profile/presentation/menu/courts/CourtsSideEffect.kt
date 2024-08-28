package profile.presentation.menu.courts

sealed interface CourtsSideEffect {

    data class OpenGoogleMaps(val url: String): CourtsSideEffect
}
