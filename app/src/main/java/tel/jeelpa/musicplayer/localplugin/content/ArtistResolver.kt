package tel.jeelpa.musicplayer.localplugin.content

import android.provider.MediaStore
import tel.jeelpa.musicplayer.common.clients.ArtistClient
import tel.jeelpa.musicplayer.localplugin.LocalPluginArtistClient

class ArtistResolver(
    private val customContentResolver: LocalPluginContentResolver
) {
    companion object {
        private val artistProjection = arrayOf(
            MediaStore.Audio.Media.ARTIST_ID,
            MediaStore.Audio.Media.ARTIST,
        )
    }

    fun getByTrack(id: String) = queryArtists(
        selection = "${MediaStore.Audio.Media._ID} = ?",
        selectionArgs = arrayOf(id)
    )

    fun getByAlbum(id: String) = queryArtists(
        selection = "${MediaStore.Audio.Media.ALBUM_ID} = ?",
        selectionArgs = arrayOf(id)
    )

    fun getById(id: String) = queryArtists(
        selection = "${MediaStore.Audio.Media.ARTIST_ID} = ?",
        selectionArgs = arrayOf(id)
    ).single()

    fun getAll() = queryArtists()

    private fun queryArtists(
        selection: String? = null,
        selectionArgs: Array<String> = emptyArray(),
        sortOrder: String? = null
    ): List<ArtistClient> {
        customContentResolver.contentResolver.query(
            AUDIO_COLLECTION,
            artistProjection,
            selection,
            selectionArgs,
            sortOrder
        )!!.use { cursor ->
            return cursor.collect {

                val idColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST_ID)
                val artistColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)

                LocalPluginArtistClient(
                    resolver = customContentResolver,
                    id = it.getLong(idColumn),
                    name = it.getString(artistColumn),
                    avatar = null,
                )
            }.distinctBy { it.getUrl() }
        }
    }
}