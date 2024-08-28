package login.ui

import Platform
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import common.navigation.GlobalNavController
import common.navigation.NavArguments
import common.navigation.ScreenNavigation
import common.navigation.ScreenNavigation.Main.createRouteWithArguments
import common.ui.theme.dimensions
import common.ui.theme.themeColors
import futibasrotterdam.composeapp.generated.resources.Res
import futibasrotterdam.composeapp.generated.resources.futibas_emblem
import getPlatform
import login.data.response.LoginAuthResponse
import login.presentation.LoginScreenState
import login.presentation.LoginSideEffect
import login.presentation.LoginViewModel
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import platform.ui.GoogleLoginButton
import platform.ui.AppleLoginButton

@OptIn(KoinExperimentalAPI::class)
@Composable
fun LoginScreen() {
    val viewModel = koinViewModel<LoginViewModel>()

    val state = viewModel.state.collectAsState().value

    LaunchedEffect(viewModel.sideEffect) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is LoginSideEffect.NavigateToMain -> {
                    GlobalNavController.navController.navigate(ScreenNavigation.Main.route)
                }
                is LoginSideEffect.NavigateToRegistration -> {
                    GlobalNavController.navController.navigate(
                        ScreenNavigation.Registration.createRouteWithArguments(
                            mapOf(
                                NavArguments.PLAYER_ID to sideEffect.playerId,
                                NavArguments.PLAYER_NAME to sideEffect.name,
                                NavArguments.PLAYER_EMAIL to sideEffect.email,
                            )
                        )
                    )
                }
            }
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(themeColors.defaultScreenBackground)
            .padding(dimensions.gap4)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(Res.drawable.futibas_emblem),
                contentDescription = "",
                contentScale = ContentScale.FillBounds,
            )
            Spacer(modifier = Modifier.height(dimensions.gap6))

            when (state) {
                is LoginScreenState.Content -> LoginButtons(viewModel)
                is LoginScreenState.Loading -> LoadingIndicator()
            }
        }
    }
}

@Composable
private fun LoginButtons(viewModel: LoginViewModel) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        GoogleButton(viewModel)

        if (getPlatform() is Platform.IOS) {
            Spacer(modifier = Modifier.height(dimensions.gap4))
            AppleButton(viewModel)
        }
    }
}

@Composable
private fun GoogleButton(
    viewModel: LoginViewModel,
) {
    GoogleLoginButton(
        onSignInResponse = { response ->
            if (response is LoginAuthResponse.Success.Google) {
                viewModel.signInPlayer(authCredential = response.authCredential)
            }
        }
    )
}

@Composable
private fun AppleButton(
    viewModel: LoginViewModel,
) {
    AppleLoginButton(
        onSignInResponse = { response ->
            if (response is LoginAuthResponse.Success.Apple) {
                viewModel.signInPlayer(appleSuccessResponse = response)
            }
        }
    )
}

@Composable
private fun LoadingIndicator() {
    CircularProgressIndicator(
        color = themeColors.secondaryColor,
        strokeWidth = dimensions.gap1,
    )
}
