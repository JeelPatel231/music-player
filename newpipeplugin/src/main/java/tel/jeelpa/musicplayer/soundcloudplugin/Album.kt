package tel.jeelpa.musicplayer.soundcloudplugin

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.schabi.newpipe.extractor.StreamingService
import tel.jeelpa.musicplayer.common.models.Album
import tel.jeelpa.musicplayer.common.models.Artist
import tel.jeelpa.musicplayer.common.models.Track

fun StreamingService.getAlbum(
    id: String,
    name: String? = null,
    cover: String? = null,
): NPAlbum {
    return NPAlbum(this,id, name, cover)
}

class NPAlbum(
    private val service: StreamingService,
    private val id: String,
    private val name: String?,
    private val cover: String?,
): Album {
    private val albumExtractor by lazy { service.getPlaylistExtractor(id) }

    override fun getUrl(): String = id

    override suspend fun getName(): String {
        if(name != null) return name
        albumExtractor.fetchPage()
        return albumExtractor.name
    }

    override suspend fun getAlbumArtists(): List<Artist> {
        albumExtractor.fetchPage()
        return listOf(albumExtractor.uploaderUrl)
            .map { service.getArtist(it, albumExtractor.uploaderName, albumExtractor.uploaderAvatarUrl) }
    }

    override suspend fun getAlbumArt(): String {
        if(cover != null) return cover
        albumExtractor.fetchPage()
        return albumExtractor.thumbnailUrl
    }


    override suspend fun getSongs(): Flow<PagingData<Track>> {
        TODO()
//        return albumExtractor.fetchAllPages().map {
//            service.getTrack(it.url, it.name, it.thumbnailUrl, it.uploaderUrl, it.uploaderName, it.uploaderAvatarUrl)
//        }
    }
}