package tel.jeelpa.musicplayer.stores

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map


class TrackerStore(
    private val appContext: Context
) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
        private val CURRENT_TRACKER = stringPreferencesKey("current_tracker")
    }

    fun getCurrentTracker() = appContext.dataStore.data.map {
        it[CURRENT_TRACKER]
    }

    suspend fun setCurrentTracker(name: String) = appContext.dataStore.edit {
        it[CURRENT_TRACKER] = name
    }
}
