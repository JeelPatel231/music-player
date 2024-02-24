package tel.jeelpa.musicplayer.plugins

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import tel.jeelpa.musicplayer.common.ClientsHolder
import tel.jeelpa.musicplayer.ytmplugin.YTMPluginClientsHolder
import tel.jeelpa.plugger.PluginRepo

class YTMStaticRepo : PluginRepo<ClientsHolder> {
    override fun getAllPlugins(): Flow<List<ClientsHolder>> {
        return flowOf(listOf(YTMPluginClientsHolder()))
    }
}