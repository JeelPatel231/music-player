package tel.jeelpa.musicplayer.config

import android.content.Context
import android.content.res.Resources


fun Int.toPx(context: Context): Int = (this * context.resources.displayMetrics.density).toInt()
fun Int.toDp(context: Context): Int = (this / context.resources.displayMetrics.density).toInt()

// avoid using this but its still available
val Float.toPx get() = this * Resources.getSystem().displayMetrics.density
val Float.toDp get() = this / Resources.getSystem().displayMetrics.density


val Int.toPx get() = (this * Resources.getSystem().displayMetrics.density).toInt()
val Int.toDp get() = (this / Resources.getSystem().displayMetrics.density).toInt()



object Configuration {
    private const val BOTTOM_NAV_HEIGHT = 100
    const val PEEK_HEIGHT = 2 * BOTTOM_NAV_HEIGHT // in dp
    const val QUEUE_PEEK_HEIGHT = BOTTOM_NAV_HEIGHT // in dp
}