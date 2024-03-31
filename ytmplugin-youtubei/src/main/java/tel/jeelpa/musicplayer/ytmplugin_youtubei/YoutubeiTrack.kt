package tel.jeelpa.musicplayer.ytmplugin_youtubei

import androidx.paging.PagingData
import dev.toastbits.ytmkt.impl.youtubei.YoutubeiApi
import dev.toastbits.ytmkt.model.external.ThumbnailProvider
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import tel.jeelpa.musicplayer.common.models.AbstractMediaSource
import tel.jeelpa.musicplayer.common.models.Album
import tel.jeelpa.musicplayer.common.models.Artist
import tel.jeelpa.musicplayer.common.models.StringMediaSource
import tel.jeelpa.musicplayer.common.models.Track

fun YoutubeiApi.getTrack(id: String, name: String, thumbnail: String): YoutubeiTrack {
    return YoutubeiTrack(this, id, name, thumbnail)
}

// NOT THREAD SAFE, USE MUTEX
data class CacheHolder<T>(private val cb: suspend () -> T) {
    private val value = GlobalScope.async(Dispatchers.IO, start = CoroutineStart.LAZY) {
        cb()
    }

    suspend fun get() = value.await()
}
class YoutubeiTrack(
    private val apiClient: YoutubeiApi,
    private val id: String,
    private val name: String,
    private val thumbnail: String,
) : Track {
    override fun getUrl(): String = id

    override suspend fun getName(): String = name

    private val cached = CacheHolder { apiClient.LoadSong.loadSong(id).getOrThrow() }

    override suspend fun getMediaSource(): List<AbstractMediaSource> {
        return apiClient.VideoFormats.getVideoFormats(id).getOrThrow()
            .sortedBy { it.bitrate }
            .filter { it.mimeType.startsWith("audio/mp4") }
            .mapNotNull { it.url }
            .map { StringMediaSource(it) }
    }

    override suspend fun getRadio(): Flow<PagingData<Track>> {
        TODO("Not yet implemented")
    }

    override suspend fun getCover(): String = thumbnail

    override suspend fun getArtists(): List<Artist> {
        return listOfNotNull(cached.get().artist).map {
            apiClient.getArtist(
                it.id,
                it.name ?: "Unknown Artist",
                it.thumbnail_provider?.getThumbnailUrl(ThumbnailProvider.Quality.LOW)
            )
        }
    }

    override suspend fun getAlbum(): Album {
        val album = cached.get().album!!
        val artistDetails = album.artist!!
        val artist = apiClient.getArtist(
            artistDetails.id,
            artistDetails.name!!,
            artistDetails.thumbnail_provider!!.getThumbnailUrl(ThumbnailProvider.Quality.LOW)
        )
        return apiClient.getAlbum(
            album.id,
            album.name!!,
            artist,
            album.thumbnail_provider!!.getThumbnailUrl(ThumbnailProvider.Quality.LOW)!!
        )
    }
}