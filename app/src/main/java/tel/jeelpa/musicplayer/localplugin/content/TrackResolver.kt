package tel.jeelpa.musicplayer.localplugin.content

import android.content.ContentUris
import android.net.Uri
import android.provider.MediaStore
import androidx.core.database.getStringOrNull
import tel.jeelpa.musicplayer.localplugin.LocalPluginTrack

class TrackResolver(
    private val contentResolver: LocalPluginContentResolver
) {
    companion object {
        private val trackProjection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.DISPLAY_NAME,
            MediaStore.Audio.Media.ALBUM_ID,
            MediaStore.Audio.Media.ARTIST_ID,
            MediaStore.Audio.Media.SIZE,
        )
    }

    fun getByAlbum(id: String) = queryTracks(
        selection = "${MediaStore.Audio.Media.ALBUM_ID} = ?",
        selectionArgs = arrayOf(id)
    )

    fun getByArtist(id: String) = queryTracks(
        selection = "${MediaStore.Audio.Media.ARTIST_ID} = ?",
        selectionArgs = arrayOf(id)
    )

    fun getById(id: String) = queryTracks(
        selection = "${MediaStore.Audio.Media._ID} = ?",
        selectionArgs = arrayOf(id)
    ).single()

    fun getAll() = queryTracks()

    fun search(query: String) = queryTracks(
        selection = "${MediaStore.Audio.Media.TITLE} LIKE ? OR ${MediaStore.Audio.Media.DISPLAY_NAME} LIKE ?",
        selectionArgs = arrayOf("%$query%", "%$query%")
    )

    private fun queryTracks(
        selection: String? = null,
        selectionArgs: Array<String> = emptyArray(),
        sortOrder: String? = null
    ): List<LocalPluginTrack> {
        contentResolver.contentResolver.query(
            AUDIO_COLLECTION,
            trackProjection,
            selection,
            selectionArgs,
            sortOrder
        )!!.use { cursor ->
            return cursor.collect {
                // columns
                val idColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
                val nameColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
                val displayNameColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME)
                val albumIdColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID)

                // values retrieved from columns
                val id = it.getLong(idColumn)
                val trackUri = ContentUris.withAppendedId(
                    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                    it.getLong(idColumn)
                )
                val albumArt = ContentUris.withAppendedId(
                    Uri.parse("content://media/external/audio/albumart"),
                    it.getLong(albumIdColumn)
                )


                LocalPluginTrack(
                    id = id,
                    url = trackUri,
                    name = it.getStringOrNull(nameColumn) ?: it.getString(displayNameColumn),
                    albumId = it.getString(albumIdColumn),
                    cover = albumArt,
                    customContentResolver = contentResolver
                )
            }.distinctBy { it.getUrl() }
        }
    }
}