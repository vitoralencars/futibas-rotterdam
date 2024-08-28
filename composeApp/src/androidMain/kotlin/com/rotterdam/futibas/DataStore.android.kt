package com.rotterdam.futibas

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import common.util.DATA_STORE_FILE_NAME

fun createDataStore(context: Context): DataStore<Preferences> = common.util.createDataStore {
    context.filesDir.resolve(DATA_STORE_FILE_NAME).absolutePath
}
