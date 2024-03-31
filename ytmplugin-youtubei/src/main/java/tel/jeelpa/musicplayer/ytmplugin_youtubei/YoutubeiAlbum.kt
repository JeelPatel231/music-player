package tel.jeelpa.musicplayer.ytmplugin_youtubei

import androidx.paging.PagingData
import dev.toastbits.ytmkt.impl.youtubei.YoutubeiApi
import dev.toastbits.ytmkt.model.external.ThumbnailProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import tel.jeelpa.musicplayer.common.models.Album
import tel.jeelpa.musicplayer.common.models.Artist
import tel.jeelpa.musicplayer.common.models.Track

fun YoutubeiApi.getAlbum(
    id: String,
    name: String,
    artist: Artist?,
    thumbnail: String,
): YoutubeiAlbum {
    return YoutubeiAlbum(this, id, name, artist, thumbnail)
}

class YoutubeiAlbum(
    private val apiClient: YoutubeiApi,
    private val id: String,
    private val name: String,
    private val artist: Artist?,
    private val thumbnail: String,
) : Album {
    override fun getUrl(): String = id

    override suspend fun getName(): String = name

    override suspend fun getAlbumArtists(): List<Artist> = artist?.let { listOf(it) } ?: emptyList()
    override suspend fun getAlbumArt(): String = thumbnail

    override suspend fun getSongs(): Flow<PagingData<Track>> {
        return flowOf(apiClient.LoadPlaylist.loadPlaylist(id).getOrThrow().items!!.map {
            apiClient.getTrack(
                it.id,
                it.name!!,
                it.thumbnail_provider!!.getThumbnailUrl(ThumbnailProvider.Quality.LOW)!!,
            )
        }.toPagedData())
    }
}