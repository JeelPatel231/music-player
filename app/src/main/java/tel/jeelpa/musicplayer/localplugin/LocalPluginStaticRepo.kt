package tel.jeelpa.musicplayer.localplugin

import android.content.ContentResolver
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import tel.jeelpa.musicplayer.common.ClientsHolder
import tel.jeelpa.musicplayer.localplugin.content.LocalPluginContentResolver
import tel.jeelpa.plugger.PluginRepo

class LocalPluginStaticRepo(
    contentResolver: ContentResolver
): PluginRepo<ClientsHolder> {
    private val localResolver = LocalPluginContentResolver(contentResolver)

    private val instantiated = LocalPluginClientsHolder(localResolver)

    override fun getAllPlugins(): Flow<List<ClientsHolder>> =
        flowOf(listOf(instantiated))
}