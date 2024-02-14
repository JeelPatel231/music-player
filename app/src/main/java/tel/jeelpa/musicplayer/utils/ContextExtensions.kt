package tel.jeelpa.musicplayer.utils

import android.content.Context
import android.widget.Toast

fun Context.showToast(data: String, time: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, data, time).show()
}
