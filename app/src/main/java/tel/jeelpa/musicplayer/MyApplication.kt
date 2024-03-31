package tel.jeelpa.musicplayer

import android.app.Application
import android.content.ComponentName
import android.content.Intent
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.google.android.material.color.DynamicColors
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        DynamicColors.applyToActivitiesIfAvailable(this)

        val sessionToken = SessionToken(this, ComponentName(this, PlaybackService::class.java))
        MediaController.Builder(applicationContext, sessionToken).buildAsync()

        // add uncaught exceptions custom handler
        Thread.setDefaultUncaughtExceptionHandler { thread, exception ->
            exception.printStackTrace()
            handleUncaughtExceptions(thread, exception)
        }

    }

    private fun handleUncaughtExceptions(thread: Thread, e: Throwable){
        val errorStackTrace = e.stackTraceToString()

        val intent = Intent().apply {
            action = "tel.jeelpa.musicplayer.SEND_LOG"
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            putExtra(Intent.EXTRA_TEXT, errorStackTrace)
        }
        startActivity(intent)
        Runtime.getRuntime().exit(0)
    }
}
