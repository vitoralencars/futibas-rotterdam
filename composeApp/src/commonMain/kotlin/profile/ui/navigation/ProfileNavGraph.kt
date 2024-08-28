package profile.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import components.bottomsheet.BottomSheetManager
import profile.ui.ProfileGeneralScreen
import profile.ui.menu.CourtsScreen
import profile.ui.menu.NewGameListScreen
import profile.ui.menu.PendingPlayersScreen
import profile.ui.menu.PersonalDataScreen

@Composable
fun ProfileNavGraph(
    navHostController: NavHostController,
    bottomSheetManager: BottomSheetManager,
) {
    NavHost(
        navController = navHostController,
        startDestination = ProfileScreenNavigation.PersonalGeneral.route,
    ) {
        composable(route = ProfileScreenNavigation.PersonalGeneral.route) {
            ProfileGeneralScreen(
                navController = navHostController,
                bottomSheetManager = bottomSheetManager,
            )
        }
        composable(route = ProfileScreenNavigation.PersonalData.route) {
            PersonalDataScreen(
                navController = navHostController,
                bottomSheetManager = bottomSheetManager,
            )
        }
        composable(route = ProfileScreenNavigation.Courts.route) {
            CourtsScreen(
                navController = navHostController,
            )
        }
        composable(route = ProfileScreenNavigation.NewGameList.route) {
            NewGameListScreen(
                navController = navHostController,
                bottomSheetManager = bottomSheetManager,
            )
        }
        composable(route = ProfileScreenNavigation.PendingPlayers.route) {
            PendingPlayersScreen(
                navController = navHostController,
                bottomSheetManager = bottomSheetManager,
            )
        }
    }
}
