package common.util

import androidx.datastore.preferences.core.stringPreferencesKey

object PrefsKeys {
    val LOGGED_IN_PLAYER_KEY = stringPreferencesKey("LOGGED_IN_PLAYER_KEY")
    val FCM_TOKEN_KEY = stringPreferencesKey("FCM_TOKEN_KEY")
    val APPLE_PROVIDED_PLAYER_NAME = stringPreferencesKey("APPLE_PROVIDED_PLAYER_NAME")
}
