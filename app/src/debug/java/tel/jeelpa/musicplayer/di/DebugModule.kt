package tel.jeelpa.musicplayer.di

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import tel.jeelpa.musicplayer.common.ClientsHolder
import tel.jeelpa.musicplayer.localplugin.LocalPluginClientsHolder
import tel.jeelpa.musicplayer.localplugin.content.LocalPluginContentResolver
import tel.jeelpa.musicplayer.musicservices.DebugCurrentServiceHolder
import tel.jeelpa.musicplayer.musicservices.GetCurrentClient
import tel.jeelpa.musicplayer.soundcloudplugin.SCPluginClientsHolder
import tel.jeelpa.musicplayer.stores.TrackerStore
import tel.jeelpa.musicplayer.ytmplugin.YTMPluginClientsHolder
import tel.jeelpa.plugger.PluginRepo
import tel.jeelpa.plugger.RepoComposer
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DebugModule {

    @Provides
    @Singleton
    fun providesPluginLoader(
        application: Application
    ): PluginRepo<ClientsHolder> {
        return RepoComposer()
    }


//    @Provides
//    @Singleton
//    fun providesPluginLoader(
//        application: Application
//    ): PluginRepo<ClientsHolder> {
//        // init the core loader
//        val loader = AndroidPluginLoader(application)
//        //
//        val fsPlConf = FilePluginConfig(application.filesDir.absolutePath, ".plug")
//        val apkPlConf = PluginConfiguration(packagePrefix = "tel.jeelpa.musicplayer")
//        // create 2 loaders and merge them into 1
//        return RepoComposer(
//            FileSystemPluginLoader(application, fsPlConf, loader),
//            ApkPluginLoader(application, apkPlConf, loader),
//        )
//    }

    @Provides
    @Singleton
    fun currentServiceHolder(
        application: Application,
        loader: PluginRepo<ClientsHolder>,
        store: TrackerStore,
    ): GetCurrentClient {
//        return CurrentServiceHolder(store, loader)
        val localPluginContentResolver = LocalPluginContentResolver(application.contentResolver)
        return DebugCurrentServiceHolder(LocalPluginClientsHolder(localPluginContentResolver))
        return DebugCurrentServiceHolder(SCPluginClientsHolder())
        return DebugCurrentServiceHolder(YTMPluginClientsHolder())
    }

}
