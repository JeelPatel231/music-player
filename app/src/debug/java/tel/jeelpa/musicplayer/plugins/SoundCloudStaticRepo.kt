package tel.jeelpa.musicplayer.plugins

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import tel.jeelpa.musicplayer.common.ClientsHolder
import tel.jeelpa.musicplayer.soundcloudplugin.SCPluginClientsHolder
import tel.jeelpa.plugger.PluginRepo

class SoundCloudStaticRepo : PluginRepo<ClientsHolder> {
    override fun getAllPlugins(): Flow<List<ClientsHolder>> {
        return flowOf(listOf(SCPluginClientsHolder()))
    }
}