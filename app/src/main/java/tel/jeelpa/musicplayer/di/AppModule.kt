package tel.jeelpa.musicplayer.di

import android.app.Application
import androidx.media3.exoplayer.ExoPlayer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import tel.jeelpa.musicplayer.exoplayer.ExoplayerListenerAdapter
import tel.jeelpa.musicplayer.player.AppPlayer
import tel.jeelpa.musicplayer.player.FlowPlayerListener
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
        val exo = ExoPlayer.Builder(application).build().apply { prepare() }
        return ExoplayerListenerAdapter(exo)
    }
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
