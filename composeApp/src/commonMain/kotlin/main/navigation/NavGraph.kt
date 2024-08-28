package main.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import common.navigation.NavArguments
import common.navigation.ScreenNavigation
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
            val email = backStackEntry.arguments?.getString(NavArguments.EMAIL)
            if (playerId != null && email != null) {
                RegistrationScreen(firebaseAccount = FirebaseAccount(
                    uid = playerId,
                    email = email,
                ))
            }
        }
        composable(route = ScreenNavigation.Main.route) {
            MainScreen()
        }
    }
}
