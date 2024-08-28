package main.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import common.navigation.NavArguments
import common.navigation.ScreenNavigation
import common.util.extension.checkEmptyTag
import login.domain.model.FirebaseAccount
import login.ui.LoginScreen
import main.ui.MainScreen
import registration.ui.RegistrationScreen
import splash.ui.SplashScreen

@Composable
fun NavGraph(
    navHostController: NavHostController,
) {
    NavHost(
        navController = navHostController,
        startDestination = ScreenNavigation.Splash.route,
    ) {
        composable(route = ScreenNavigation.Splash.route) {
            SplashScreen()
        }
        composable(route = ScreenNavigation.Login.route) {
            LoginScreen()
        }
        composable(route = ScreenNavigation.Registration.route) { backStackEntry ->
            val playerId = backStackEntry.arguments?.getString(NavArguments.PLAYER_ID)
            val name = backStackEntry.arguments?.getString(NavArguments.PLAYER_NAME)
            val email = backStackEntry.arguments?.getString(NavArguments.PLAYER_EMAIL)
            if (playerId != null && name != null && email != null) {
                RegistrationScreen(firebaseAccount = FirebaseAccount(
                    uid = playerId.checkEmptyTag(),
                    name = name.checkEmptyTag(),
                    email = email.checkEmptyTag(),
                ))
            }
        }
        composable(route = ScreenNavigation.Main.route) {
            MainScreen()
        }
    }
}
