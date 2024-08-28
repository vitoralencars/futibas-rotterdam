package login.presentation

import androidx.lifecycle.viewModelScope
import common.base.BaseViewModel
import kotlinx.coroutines.launch
import login.domain.model.LoginPlayerRequest
import login.domain.usecase.LoginPlayerUseCase
import network.ServiceResult

class LoginViewModel(
    private val logInPlayer: LoginPlayerUseCase,
) : BaseViewModel<LoginScreenState, LoginSideEffect>(
    initialState = LoginScreenState.Content
) {

    fun getPlayerInfo(
        playerId: String,
        email: String,
    ) {
        updateState {
            LoginScreenState.Loading
        }

        viewModelScope.launch {
            val response = logInPlayer(LoginPlayerRequest(
                playerId = playerId,
            ))

            when (response) {
                is ServiceResult.Success -> {
                    if (response.response != null) {
                        emitSideEffect(LoginSideEffect.NavigateToMain)
                    } else {
                        emitSideEffect(LoginSideEffect.NavigateToRegistration(
                            playerId = playerId,
                            email = email,
                        ))
                    }
                }
                is ServiceResult.Error -> {}
            }

            updateState {
                LoginScreenState.Content
            }
        }
    }
}
