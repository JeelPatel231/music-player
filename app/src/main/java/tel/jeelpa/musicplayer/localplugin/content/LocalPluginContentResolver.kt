package tel.jeelpa.musicplayer.localplugin.content

import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.provider.MediaStore


inline fun <reified T> Cursor.collect(callback: (Cursor) -> T): Array<T> {
    val collection = arrayOfNulls<T>(count)

    while (moveToNext()) {
        collection[position] = callback(this)
    }
    return collection as Array<T>
}

val AUDIO_COLLECTION: Uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
    MediaStore.Audio.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
} else {
    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
}

class LocalPluginContentResolver(val contentResolver: ContentResolver) {
    val albumResolver = AlbumResolver(this)
    val trackResolver = TrackResolver(this)
    val artistResolver = ArtistResolver(this)
}