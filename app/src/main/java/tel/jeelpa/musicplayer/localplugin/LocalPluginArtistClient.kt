package tel.jeelpa.musicplayer.localplugin

import android.net.Uri
import tel.jeelpa.musicplayer.common.clients.AlbumClient
import tel.jeelpa.musicplayer.common.clients.ArtistClient
import tel.jeelpa.musicplayer.common.clients.TrackClient
import tel.jeelpa.musicplayer.localplugin.content.LocalPluginContentResolver

class LocalPluginArtistClient(
    private val resolver: LocalPluginContentResolver,
    private val id: Long,
    private val name: String,
    private val avatar: Uri?
): ArtistClient {
    override fun getUrl(): String = id.toString()

    override suspend fun getName(): String = name

    override suspend fun getAvatar(): String? = avatar?.toString()

    override suspend fun getSongs(offset: Int, limit: Int): List<TrackClient> {
        return resolver.trackResolver.getByArtist(id.toString())
    }

    override suspend fun getAlbums(offset: Int, limit: Int): List<AlbumClient> {
        return resolver.albumResolver.getByArtist(id.toString())
    }

    override suspend fun getSingles(offset: Int, limit: Int): List<AlbumClient> {
        TODO("Not yet implemented")
    }
}