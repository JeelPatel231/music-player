package tel.jeelpa.musicplayer.musicservices

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import tel.jeelpa.musicplayer.common.ClientsHolder

data class DebugCurrentServiceHolder(
    private val plugin: ClientsHolder
): GetCurrentClient {
    override operator fun invoke(): Flow<ClientsHolder> =
        flowOf(plugin)
}
