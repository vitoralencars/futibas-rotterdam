package platform.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import createDataStore
import org.koin.dsl.module

actual val platformModule = module {
    single<DataStore<Preferences>> { createDataStore() }
}
