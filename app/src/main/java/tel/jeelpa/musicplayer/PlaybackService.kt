package tel.jeelpa.musicplayer

import android.content.Intent
import androidx.media3.common.Player
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

//const val FROM_NOTIFICATION_KEY = "tel.jeelpa.intent.FROM_NOTIFICATION"

@AndroidEntryPoint
class PlaybackService: MediaSessionService() {
    private var mediaSession: MediaSession? = null

    @Inject
    lateinit var player: Player

    // Create your player and media session in the onCreate lifecycle event
    override fun onCreate() {
        super.onCreate()

//        val intent = Intent(this, MainActivity::class.java)
//            .setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT)
//            .putExtra(FROM_NOTIFICATION_KEY, true)

//        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        mediaSession = MediaSession.Builder(this, player)
//            .setSessionActivity(pendingIntent)
            .build()
    }

    // The user dismissed the app from the recent tasks
    override fun onTaskRemoved(rootIntent: Intent?) {
        val player = mediaSession?.player!!
        if (!player.playWhenReady || player.mediaItemCount == 0) {
            // Stop the service if not playing, continue playing in the background
            // otherwise.
            stopSelf()
        }
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession? =
        mediaSession

    // Remember to release the player and media session in onDestroy
    override fun onDestroy() {
        mediaSession?.run {
            player.release()
            release()
            mediaSession = null
        }
        super.onDestroy()
    }
}


