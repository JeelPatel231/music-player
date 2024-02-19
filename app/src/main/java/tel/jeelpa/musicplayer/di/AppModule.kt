package tel.jeelpa.musicplayer.di

import android.app.Application
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import tel.jeelpa.musicplayer.common.ClientsHolder
import tel.jeelpa.musicplayer.exoplayer.ExoplayerListenerAdapter
import tel.jeelpa.musicplayer.musicservices.CurrentServiceHolder
import tel.jeelpa.musicplayer.musicservices.CurrentServiceHolderDevMock
import tel.jeelpa.musicplayer.musicservices.GetCurrentClient
import tel.jeelpa.musicplayer.player.AppPlayer
import tel.jeelpa.musicplayer.player.FlowPlayerListener
import tel.jeelpa.musicplayer.soundcloudplugin.SCPluginClientsHolder
import tel.jeelpa.musicplayer.stores.TrackerStore
import tel.jeelpa.musicplayer.ytmplugin.YTMPluginClientsHolder
import tel.jeelpa.plugger.PluginRepo
import tel.jeelpa.plugger.RepoComposer
import tel.jeelpa.plugger.models.PluginConfiguration
import tel.jeelpa.plugger.pluginloader.AndroidPluginLoader
import tel.jeelpa.plugger.pluginloader.apk.ApkPluginLoader
import tel.jeelpa.plugger.pluginloader.file.FilePluginConfig
import tel.jeelpa.plugger.pluginloader.file.FileSystemPluginLoader
import javax.inject.Singleton


/*******************************************
 * Providers in this class must only be used
 * for dependency injecting in other modules,
 * NOT in the application code
 ********************************************/
@Module
@InstallIn(SingletonComponent::class)
class PrivateAppModule {
    @Provides
    @Singleton
    fun providesExoplayer(
        application: Application
    ): ExoplayerListenerAdapter {
        val exo = ExoPlayer.Builder(application).build().apply {
            playWhenReady = true
            prepare()
        }
        val alwaysBeReady = object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                if(playbackState == ExoPlayer.STATE_IDLE){
                    // we want player to always be ready to play
                    exo.prepare()
                }
            }
        }
        exo.addListener(alwaysBeReady)
        return ExoplayerListenerAdapter(exo)
    }

    @Provides
    @Singleton
    fun providesPluginLoader(
        application: Application
    ): PluginRepo<ClientsHolder> {
        // init the core loader
        val loader = AndroidPluginLoader(application)
        //
        val fsPlConf = FilePluginConfig(application.filesDir.absolutePath, ".plug")
        val apkPlConf = PluginConfiguration(packagePrefix = "tel.jeelpa.musicplayer")
        // create 2 loaders and merge them into 1
        return RepoComposer(
            FileSystemPluginLoader(application, fsPlConf, loader),
            ApkPluginLoader(application, apkPlConf, loader)
        )
    }

    @Provides
    @Singleton
    fun providesTrackerStore(
        application: Application
    ): TrackerStore =
        TrackerStore(application)
}

/*******************************************
 * Providers in this module are to
 * free to be used in application code
 ********************************************/
@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun providesEventForwarder(
        player: ExoplayerListenerAdapter
    ): FlowPlayerListener = player

    @Provides
    @Singleton
    fun providesPlayer(
        player: ExoplayerListenerAdapter
    ): AppPlayer = player

    @Provides
    @Singleton
    fun currentServiceHolder(
        loader: PluginRepo<ClientsHolder>,
        store: TrackerStore,
    ): GetCurrentClient {
        // TODO: remove from release
        return CurrentServiceHolderDevMock(YTMPluginClientsHolder())
        return CurrentServiceHolderDevMock(SCPluginClientsHolder())
        return CurrentServiceHolder(store, loader)
    }
}
