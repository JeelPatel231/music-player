package tel.jeelpa.musicplayer.localplugin

import android.net.Uri
import tel.jeelpa.musicplayer.common.models.Album
import tel.jeelpa.musicplayer.common.models.Artist
import tel.jeelpa.musicplayer.common.models.Track
import tel.jeelpa.musicplayer.localplugin.content.LocalPluginContentResolver

class LocalPluginAlbum(
    private val resolver: LocalPluginContentResolver,
    private val id: Long,
    private val name: String,
    private val art: Uri,
) : Album {
    override fun getUrl(): String = id.toString()

    override suspend fun getName(): String = name

    override suspend fun getAlbumArtists(): List<Artist> {
        return resolver.artistResolver.getByAlbum(id.toString())
    }

    override suspend fun getAlbumArt(): String = art.toString()

    override suspend fun getSongs(offset: Int, limit: Int): List<Track> {
        return resolver.trackResolver.getByAlbum(id.toString())
    }
}