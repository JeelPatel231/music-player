package tel.jeelpa.musicplayer.localplugin

import android.net.Uri
import tel.jeelpa.musicplayer.common.clients.AlbumClient
import tel.jeelpa.musicplayer.common.clients.ArtistClient
import tel.jeelpa.musicplayer.common.clients.TrackClient
import tel.jeelpa.musicplayer.localplugin.content.LocalPluginContentResolver

class LocalPluginAlbumClient(
    private val resolver: LocalPluginContentResolver,
    private val id: Long,
    private val name: String,
    private val art: Uri,
) : AlbumClient {
    override fun getUrl(): String = id.toString()

    override suspend fun getName(): String = name

    override suspend fun getAlbumArtists(): List<ArtistClient> {
        return resolver.artistResolver.getByAlbum(id.toString())
    }

    override suspend fun getAlbumArt(): String = art.toString()

    override suspend fun getSongs(offset: Int, limit: Int): List<TrackClient> {
        return resolver.trackResolver.getByAlbum(id.toString())
    }
}