package tel.jeelpa.musicplayer.di

import android.app.Application
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import tel.jeelpa.musicplayer.player.AppPlayer
import tel.jeelpa.musicplayer.player.FlowPlayerListener
import tel.jeelpa.musicplayer.player.exoplayerimpl.ExoplayerListenerAdapter
import tel.jeelpa.musicplayer.stores.TrackerStore
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

}
