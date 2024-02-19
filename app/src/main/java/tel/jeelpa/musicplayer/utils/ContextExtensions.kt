package tel.jeelpa.musicplayer.utils

import android.content.Context
import android.content.res.Resources
import android.widget.Toast

fun Context.showToast(data: String, time: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, data, time).show()
}

fun Int.toPx(context: Context): Int = (this * context.resources.displayMetrics.density).toInt()
fun Int.toDp(context: Context): Int = (this / context.resources.displayMetrics.density).toInt()

// avoid using this but its still available
val Float.toPx get() = this * Resources.getSystem().displayMetrics.density
val Float.toDp get() = this / Resources.getSystem().displayMetrics.density


val Int.toPx get() = (this * Resources.getSystem().displayMetrics.density).toInt()
val Int.toDp get() = (this / Resources.getSystem().displayMetrics.density).toInt()
