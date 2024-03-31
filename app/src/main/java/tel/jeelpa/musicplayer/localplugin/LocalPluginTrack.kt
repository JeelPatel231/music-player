package tel.jeelpa.musicplayer.localplugin

import android.net.Uri
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import tel.jeelpa.musicplayer.common.models.AbstractMediaSource
import tel.jeelpa.musicplayer.common.models.Album
import tel.jeelpa.musicplayer.common.models.Artist
import tel.jeelpa.musicplayer.common.models.StringMediaSource
import tel.jeelpa.musicplayer.common.models.Track
import tel.jeelpa.musicplayer.localplugin.content.LocalPluginContentResolver

class LocalPluginTrack(
    private val customContentResolver: LocalPluginContentResolver,
    private val id: Long,
    private val url: Uri,
    private val name: String,
    private val cover: Uri,
    private val albumId: String
): Track {
    override suspend fun getName(): String = name

    override fun getUrl(): String = id.toString()

    override suspend fun getMediaSource(): List<AbstractMediaSource> {
        return listOf(StringMediaSource(url.toString()))
    }

    override suspend fun getRadio(): Flow<PagingData<Track>> =
        /* Radio is not supported in local songs */
        emptyFlow()

    override suspend fun getCover(): String = cover.toString()

    override suspend fun getArtists(): List<Artist> {
        return customContentResolver.artistResolver.getByTrack(id.toString())
    }

    override suspend fun getAlbum(): Album {
        return customContentResolver.albumResolver.getById(albumId)
    }
}