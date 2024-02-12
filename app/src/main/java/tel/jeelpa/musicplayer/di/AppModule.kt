package tel.jeelpa.musicplayer.di

import android.app.Application
import androidx.media3.exoplayer.ExoPlayer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import tel.jeelpa.musicplayer.exoplayer.ExoplayerImpl
import tel.jeelpa.musicplayer.player.AppPlayer
import tel.jeelpa.musicplayer.player.EventForwarder
import tel.jeelpa.musicplayer.player.ListenableAppPlayer
import javax.inject.Singleton


/*******************************************
 * Providers in this class must only be used
 * for dependency injecting in other modules,
 * NOT in the application code
********************************************/
@Module
@InstallIn(SingletonComponent::class)
class PrivateAppModule {

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
    fun providesEventForwarder(): EventForwarder {
        return EventForwarder()
    }

    @Provides
    @Singleton
    fun providesPlayer(
        application: Application,
        eventForwarder: EventForwarder,
    ): AppPlayer {
        val exoplayer = ExoPlayer.Builder(application).build().apply { prepare() }
        return ListenableAppPlayer(ExoplayerImpl(exoplayer), eventForwarder)
    }

}
