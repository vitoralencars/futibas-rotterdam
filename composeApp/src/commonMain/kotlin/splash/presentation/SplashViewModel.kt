package splash.presentation

import androidx.lifecycle.viewModelScope
import common.base.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import login.domain.model.LoginPlayerRequest
import login.domain.usecase.LoginPlayerUseCase
import player.domain.usecase.RetrieveLoggedInPlayerUseCase

internal class SplashViewModel(
    private val retrieveLoggedInPlayerUseCase: RetrieveLoggedInPlayerUseCase,
    private val loginPlayerUseCase: LoginPlayerUseCase,
): BaseViewModel<SplashScreenState, SplashSideEffect>(
    initialState = SplashScreenState()
) {

    init {
        viewModelScope.launch {
            retrieveLoggedInPlayerUseCase()?.let {
                delay(SPLASH_TIME_MILLIS_WITH_INFO)
                loginPlayerUseCase(logInPlayerRequest = LoginPlayerRequest(
                    playerId = it.playerId,
                ))
                emitSideEffect(SplashSideEffect.NavigateToMain)
            } ?: run {
                delay(SPLASH_TIME_MILLIS_NO_INFO)
                emitSideEffect(SplashSideEffect.NavigateToLogIn)
            }
        }
    }

    private companion object {
        const val SPLASH_TIME_MILLIS_WITH_INFO = 1000L
        const val SPLASH_TIME_MILLIS_NO_INFO = 2000L
    }
}
