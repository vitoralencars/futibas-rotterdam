package splash.presentation

internal sealed interface SplashSideEffect {
    data object NavigateToMain : SplashSideEffect
    data object NavigateToLogIn: SplashSideEffect
}
