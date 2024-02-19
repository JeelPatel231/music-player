package tel.jeelpa.musicplayer.musicservices

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import tel.jeelpa.musicplayer.common.ClientsHolder
import tel.jeelpa.musicplayer.stores.TrackerStore
import tel.jeelpa.plugger.PluginRepo

//data class CurrentServiceHolder(private val store: TrackerStore) {
//    operator fun invoke() = store.getCurrentTracker()
//}

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
            emit(list.first { it.getName() == currentTracker })
        }
    }
}


data class CurrentServiceHolderDevMock(
    private val clientsHolder: ClientsHolder
): GetCurrentClient {
    override operator fun invoke() = flowOf(clientsHolder)
}