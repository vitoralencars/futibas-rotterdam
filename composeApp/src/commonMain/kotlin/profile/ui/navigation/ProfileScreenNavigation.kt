package profile.ui.navigation

sealed class ProfileScreenNavigation(val route: String) {
    data object PersonalGeneral : ProfileScreenNavigation(route = "PersonalGeneral")
    data object PersonalData : ProfileScreenNavigation(route = "PersonalData")
    data object Courts : ProfileScreenNavigation(route = "Courts")
    data object NewGameList : ProfileScreenNavigation(route = "NewGameList")
    data object PendingPlayers : ProfileScreenNavigation(route = "PendingPlayers")
}
