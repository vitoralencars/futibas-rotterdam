package login.presentation

import androidx.lifecycle.viewModelScope
import common.base.BaseViewModel
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.AuthCredential
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.launch
import login.data.response.LoginAuthResponse
import login.domain.model.LoginPlayerRequest
import login.domain.usecase.LoginPlayerUseCase
import network.ServiceResult

class LoginViewModel(
    private val logInPlayer: LoginPlayerUseCase,
) : BaseViewModel<LoginScreenState, LoginSideEffect>(
    initialState = LoginScreenState.Content
) {

    fun signInPlayer(authCredential: AuthCredential) {
        viewModelScope.launch {
            try {
                val signInResult = Firebase.auth.signInWithCredential(authCredential = authCredential)
                signInResult.user?.let { user ->
                    user.email?.let { email ->
                        getPlayerInfo(
                            playerId = user.uid,
                            email = email,
                        )
                    }
                }
            } catch (e: Exception) {

            }
        }
    }

    fun signInPlayer(appleSuccessResponse: LoginAuthResponse.Success.Apple) {
        viewModelScope.launch {
            getPlayerInfo(
                playerId = appleSuccessResponse.uid,
                email = appleSuccessResponse.email,
            )
        }
    }

    private fun getPlayerInfo(
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
