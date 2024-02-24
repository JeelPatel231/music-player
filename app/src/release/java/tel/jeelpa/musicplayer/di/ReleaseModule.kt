package tel.jeelpa.musicplayer.di

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import tel.jeelpa.musicplayer.common.ClientsHolder
import tel.jeelpa.musicplayer.localplugin.LocalPluginStaticRepo
import tel.jeelpa.musicplayer.musicservices.CurrentServiceHolder
import tel.jeelpa.musicplayer.musicservices.GetCurrentClient
import tel.jeelpa.musicplayer.stores.TrackerStore
import tel.jeelpa.plugger.PluginRepo
import tel.jeelpa.plugger.RepoComposer
import tel.jeelpa.plugger.pluginloader.AndroidPluginLoader
import tel.jeelpa.plugger.pluginloader.apk.ApkPluginConfiguration
import tel.jeelpa.plugger.pluginloader.apk.ApkPluginLoader
import tel.jeelpa.plugger.pluginloader.file.FilePluginConfig
import tel.jeelpa.plugger.pluginloader.file.FileSystemPluginLoader
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ReleaseModule {

    @Provides
    @Singleton
    fun providesPluginLoader(
        application: Application
    ): PluginRepo<ClientsHolder> {
        // init the core loader
        val loader = AndroidPluginLoader(application)
        //
        val fsPlConf = FilePluginConfig(application.filesDir.absolutePath, ".plug")
        val apkPlConf = ApkPluginConfiguration(packagePrefix = "tel.jeelpa.musicplayer")
        // create 2 loaders and merge them into 1
        return RepoComposer(
            LocalPluginStaticRepo(application.contentResolver),
            FileSystemPluginLoader(application, fsPlConf, loader),
            ApkPluginLoader(application, apkPlConf, loader),
        )
    }


    @Provides
    @Singleton
    fun currentServiceHolder(
        loader: PluginRepo<ClientsHolder>,
        store: TrackerStore,
    ): GetCurrentClient {
        return CurrentServiceHolder(store, loader)
    }

}
