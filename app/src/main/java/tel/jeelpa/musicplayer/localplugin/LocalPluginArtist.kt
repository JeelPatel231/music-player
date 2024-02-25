package tel.jeelpa.musicplayer.localplugin

import android.net.Uri
import tel.jeelpa.musicplayer.common.models.Album
import tel.jeelpa.musicplayer.common.models.Artist
import tel.jeelpa.musicplayer.common.models.Track
import tel.jeelpa.musicplayer.localplugin.content.LocalPluginContentResolver

class LocalPluginArtist(
    private val resolver: LocalPluginContentResolver,
    private val id: Long,
    private val name: String,
    private val avatar: Uri?
): Artist {
    override fun getUrl(): String = id.toString()

    override suspend fun getName(): String = name

    override suspend fun getAvatar(): String? = avatar?.toString()

    override suspend fun getSongs(offset: Int, limit: Int): List<Track> {
        return resolver.trackResolver.getByArtist(id.toString())
    }

    override suspend fun getAlbums(offset: Int, limit: Int): List<Album> {
        return resolver.albumResolver.getByArtist(id.toString())
    }

    override suspend fun getSingles(offset: Int, limit: Int): List<Album> {
        TODO("Not yet implemented")
    }
}