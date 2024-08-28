package splash.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import common.navigation.GlobalNavController
import common.navigation.ScreenNavigation
import common.ui.theme.dimensions
import common.ui.theme.themeColors
import futibasrotterdam.composeapp.generated.resources.Res
import futibasrotterdam.composeapp.generated.resources.futibas_emblem
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import splash.presentation.SplashSideEffect
import splash.presentation.SplashViewModel

@OptIn(KoinExperimentalAPI::class)
@Composable
fun SplashScreen() {
    val viewModel: SplashViewModel = koinViewModel<SplashViewModel>()

    LaunchedEffect(viewModel.sideEffect) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is SplashSideEffect.NavigateToMain -> {
                    GlobalNavController.navController.navigate(ScreenNavigation.Main.route) {
                        popUpTo(ScreenNavigation.Splash.route) {
                            inclusive = true
                        }
                    }
                }
                is SplashSideEffect.NavigateToLogIn -> {
                    GlobalNavController.navController.navigate(ScreenNavigation.Login.route) {
                        popUpTo(ScreenNavigation.Splash.route) {
                            inclusive = true
                        }
                    }
                }
            }
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(themeColors.defaultScreenBackground)
            .padding(dimensions.gap4),
    ) {
        Image(
            painter = painterResource(Res.drawable.futibas_emblem),
            contentDescription = "",
            contentScale = ContentScale.FillBounds,
        )
    }
}
