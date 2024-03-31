package tel.jeelpa.musicplayer.soundcloudplugin

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.schabi.newpipe.extractor.StreamingService
import tel.jeelpa.musicplayer.common.models.AbstractMediaSource
import tel.jeelpa.musicplayer.common.models.Album
import tel.jeelpa.musicplayer.common.models.Artist
import tel.jeelpa.musicplayer.common.models.StringMediaSource
import tel.jeelpa.musicplayer.common.models.Track

fun StreamingService.getTrack(
    id: String,
    name: String? = null,
    cover: String? = null,
    artistUrl: String? = null,
    artist: String? = null,
    artistAvatar: String? = null,
): Track {
    return NPServiceTrack(this, id, name, cover, artistUrl, artist, artistAvatar)
}

class NPServiceTrack(
    private val service: StreamingService,
    private val id: String,
    private val name: String?,
    private val cover: String?,

    private val artistUrl: String?,
    private val artist: String?,
    private val artistAvatar: String?,
) : Track {
    private val streamExtractor by lazy { service.getStreamExtractor(id) }

    override suspend fun getName(): String {
        if(name != null) return name
        streamExtractor.fetchPage()
        return streamExtractor.name
    }

    override fun getUrl(): String = id

    override suspend fun getMediaSource(): List<AbstractMediaSource> {
        streamExtractor.fetchPage()
        return streamExtractor.audioStreams.map { StringMediaSource(it.content) }
    }

    override suspend fun getRadio(): Flow<PagingData<Track>> {
        TODO()
//        streamExtractor.fetchPage()
//        return streamExtractor.relatedItems?.items.orEmpty()
//            .filter { it.infoType == InfoItem.InfoType.STREAM }
//            .map { service.getTrack(it.url, it.name, it.thumbnailUrl) } // TODO: no artist
    }

    override suspend fun getCover(): String {
        if(cover != null) return cover
        streamExtractor.fetchPage()
        return streamExtractor.thumbnailUrl
    }

    override suspend fun getArtists(): List<Artist> {
        if(artistUrl != null) {
            return listOf(service.getArtist(artistUrl, artist, artistAvatar))
        }
        streamExtractor.fetchPage()
        return listOf(streamExtractor.uploaderUrl)
            .map { service.getArtist(it, streamExtractor.uploaderName, streamExtractor.uploaderAvatarUrl) }
    }

    override suspend fun getAlbum(): Album {
        streamExtractor.fetchPage()
        TODO("Not yet implemented")
    }
}