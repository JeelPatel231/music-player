package tel.jeelpa.musicplayer.localplugin.content

import android.content.ContentUris
import android.net.Uri
import android.provider.MediaStore
import tel.jeelpa.musicplayer.common.models.Album
import tel.jeelpa.musicplayer.localplugin.LocalPluginAlbum

class AlbumResolver(
    private val customContentResolver: LocalPluginContentResolver
) {
    companion object {
        private val albumProjection = arrayOf(
            MediaStore.Audio.Media.ALBUM_ID,
            MediaStore.Audio.Media.ALBUM,
        )
    }

    fun getByArtist(id: String) = queryAlbums(
        selection = "${MediaStore.Audio.Media.ARTIST_ID} = ?",
        selectionArgs = arrayOf(id)
    )

    fun getById(id: String) = queryAlbums(
        selection = "${MediaStore.Audio.Media.ALBUM_ID} = ?",
        selectionArgs = arrayOf(id)
    ).single()

    fun getAll() = queryAlbums()

    fun search(query: String) = queryAlbums(
        selection = "${MediaStore.Audio.Media.ALBUM} LIKE ?",
        selectionArgs = arrayOf("%$query%"),
    )

    private fun queryAlbums(
        selection: String? = null,
        selectionArgs: Array<String> = emptyArray(),
        sortOrder: String? = null,
    ): List<Album> {
        customContentResolver.contentResolver.query(
            AUDIO_COLLECTION,
            albumProjection,
            selection,
            selectionArgs,
            sortOrder
        )!!.use { cursor ->
            return cursor.collect {
                val idColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID)
                val albumColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM)

                val id = it.getLong(idColumn)
                val coverUri = ContentUris.withAppendedId(
                    Uri.parse("content://media/external/audio/albumart"),
                    id
                )

                LocalPluginAlbum(
                    id = id,
                    name = it.getString(albumColumn),
                    art = coverUri,
                    resolver = customContentResolver,
                )
            }.distinctBy { it.getUrl() } // TODO: get unique values at DB level
        }
    }
}