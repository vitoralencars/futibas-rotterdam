package notifications.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import common.util.PrefsKeys.FCM_TOKEN_KEY
import kotlinx.coroutines.flow.first
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import network.ServiceResult
import notifications.data.service.NotificationsService
import notifications.domain.model.NewFCMToken
import notifications.domain.repository.NotificationsRepository

class NotificationsRepositoryImpl(
    private val dataStore: DataStore<Preferences>,
    private val notificationsService: NotificationsService,
) : NotificationsRepository {

    override suspend fun storeFCMToken(fcmToken: String) {
        dataStore.edit { prefs ->
            prefs[FCM_TOKEN_KEY] = Json.encodeToString(fcmToken)
        }
    }

    override suspend fun retrieveFCMToken(): String {
        val storedFCMToken = dataStore.data.first()[FCM_TOKEN_KEY]

        return storedFCMToken?.let { fcmToken ->
            Json.decodeFromString(fcmToken)
        } ?: ""
    }

    override suspend fun updateFCMToken(fcmToken: NewFCMToken): ServiceResult<Any?> {
        return when (val result = notificationsService.updateFCMToken(fcmToken)) {
            is ServiceResult.Success -> result
            is ServiceResult.Error -> ServiceResult.Error(
                Throwable(message = result.exception.message)
            )
        }
    }
}
