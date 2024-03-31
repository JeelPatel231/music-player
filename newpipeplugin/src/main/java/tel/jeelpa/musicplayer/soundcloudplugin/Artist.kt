package tel.jeelpa.musicplayer.soundcloudplugin

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.schabi.newpipe.extractor.StreamingService
import tel.jeelpa.musicplayer.common.models.Album
import tel.jeelpa.musicplayer.common.models.Artist
import tel.jeelpa.musicplayer.common.models.Track

fun StreamingService.getArtist(
    id: String,
    name: String? = null,
    avatar: String? = null,
): Artist {
    return NPArtist(this, id, name, avatar)
}

class NPArtist(
    private val service: StreamingService,
    private val id: String,
    private val name: String?,
    private val avatar: String?,
): Artist {
    private val artistExtractor by lazy { service.getChannelExtractor(id) }

    override fun getUrl(): String = id

    override suspend fun getName(): String {
        if (name != null) return name
        artistExtractor.fetchPage()
        return artistExtractor.name
    }

    override suspend fun getAvatar(): String {
        if(avatar != null) return avatar
        artistExtractor.fetchPage()
        return artistExtractor.avatarUrl
    }

    override suspend fun getSongs(): Flow<PagingData<Track>> {
        TODO()
//        artistExtractor.fetchPage()
//        return artistExtractor.initialPage.items
//            .filter { it.infoType == InfoItem.InfoType.STREAM }
//            .map { service.getTrack(it.url, it.name, it.thumbnailUrl, it.uploaderUrl, it.uploaderName, it.uploaderAvatarUrl) }
    }

    override suspend fun getAlbums(): Flow<PagingData<Album>> {
        TODO()
//        artistExtractor.fetchPage()
//        return artistExtractor.initialPage.items
//            .filter { it.infoType == InfoItem.InfoType.PLAYLIST }
//            .map { service.getAlbum(it.url, it.name, it.thumbnailUrl) }
    }

    override suspend fun getSingles(): Flow<PagingData<Album>> {
        TODO()
//        artistExtractor.fetchPage()
//        return artistExtractor.initialPage.items
//            .filter { it.infoType == InfoItem.InfoType.PLAYLIST }
//            .map { service.getAlbumClient(it.url) }
    }
}