package tel.jeelpa.musicplayer.musicservices

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import tel.jeelpa.musicplayer.common.ClientsHolder
import tel.jeelpa.musicplayer.localplugin.OFFLINE_EXTENSION_NAME
import tel.jeelpa.musicplayer.stores.TrackerStore
import tel.jeelpa.plugger.PluginRepo

interface GetCurrentClient {
    operator fun invoke(): Flow<ClientsHolder>
}

data class CurrentServiceHolder(
    private val store: TrackerStore,
    private val loader: PluginRepo<ClientsHolder>
): GetCurrentClient {
    override operator fun invoke(): Flow<ClientsHolder> = flow {
        val currentTracker = store.getCurrentTracker().first()
        loader.getAllPlugins().collect { list ->
            val found = list.find { it.getName() == currentTracker }
                ?: list.find { it.getName() == OFFLINE_EXTENSION_NAME }!!

            emit(found)
        }
    }
}
