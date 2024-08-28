package login.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import common.util.PrefsKeys.APPLE_PROVIDED_PLAYER_NAME
import kotlinx.coroutines.flow.first
import player.domain.model.LoggedInPlayer
import login.data.service.LoginService
import login.domain.model.LoginPlayerRequest
import login.domain.repository.LoginRepository
import network.ServiceResult
import notifications.domain.usecase.UpdateFCMTokenUseCase
import player.domain.usecase.StoreLoggedInPlayerUseCase

class LoginRepositoryImpl(
    private val service: LoginService,
    private val dataStore: DataStore<Preferences>,
    private val storeLoggedInPlayerUseCase: StoreLoggedInPlayerUseCase,
    private val updateFCMTokenUseCase: UpdateFCMTokenUseCase,
) : LoginRepository {

    override suspend fun loginPlayer(
        loginPlayerRequest: LoginPlayerRequest,
    ): ServiceResult<LoggedInPlayer?> {

        return when (val result = service.loginPlayer(loginPlayerRequest)) {
            is ServiceResult.Success -> {
                result.response?.loggedInPlayer?.let {
                    storeLoggedInPlayerUseCase(it)
                    updateFCMTokenUseCase()
                }
                ServiceResult.Success(
                    response = result.response?.loggedInPlayer
                )
            }
            is ServiceResult.Error -> ServiceResult.Error(
                Throwable(message = result.exception.message)
            )
        }
    }

    override suspend fun storeAppleProvidedName(name: String) {
        dataStore.edit { prefs ->
            prefs[APPLE_PROVIDED_PLAYER_NAME] = name
        }
    }

    override suspend fun retrieveAppleProvidedStoredName(): String {
        return dataStore.data.first()[APPLE_PROVIDED_PLAYER_NAME].orEmpty()
    }
}
