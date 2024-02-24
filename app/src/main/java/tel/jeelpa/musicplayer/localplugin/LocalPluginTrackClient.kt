package tel.jeelpa.musicplayer.localplugin

import android.net.Uri
import tel.jeelpa.musicplayer.common.clients.AbstractMediaSource
import tel.jeelpa.musicplayer.common.clients.AlbumClient
import tel.jeelpa.musicplayer.common.clients.ArtistClient
import tel.jeelpa.musicplayer.common.clients.StringMediaSource
import tel.jeelpa.musicplayer.common.clients.TrackClient
import tel.jeelpa.musicplayer.localplugin.content.LocalPluginContentResolver

class LocalPluginTrackClient(
    private val customContentResolver: LocalPluginContentResolver,
    private val id: Long,
    private val url: Uri,
    private val name: String,
    private val cover: Uri,
    private val albumId: String
): TrackClient {
    override suspend fun getName(): String = name

    override fun getUrl(): String = id.toString()

    override suspend fun getMediaSource(): List<AbstractMediaSource> {
        return listOf(StringMediaSource(url.toString()))
    }

    override suspend fun getRadio(): List<TrackClient> {
        TODO("Not yet implemented")
    }

    override suspend fun getCover(): String = cover.toString()

    override suspend fun getArtists(): List<ArtistClient> {
        return customContentResolver.artistResolver.getByTrack(id.toString())
    }

    override suspend fun getAlbum(): AlbumClient {
        return customContentResolver.albumResolver.getById(albumId)
    }
}