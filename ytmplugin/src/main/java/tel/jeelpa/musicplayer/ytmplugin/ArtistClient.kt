package tel.jeelpa.musicplayer.ytmplugin

import org.schabi.newpipe.extractor.InfoItem
import org.schabi.newpipe.extractor.services.youtube.YoutubeService
import tel.jeelpa.musicplayer.common.models.Album
import tel.jeelpa.musicplayer.common.models.Artist
import tel.jeelpa.musicplayer.common.models.Track

fun YoutubeService.getArtistClient(
    id: String,
    name: String? = null,
    cover: String? = null,
): Artist {
    return YTMArtist(this, id, name, cover)
}

class YTMArtist(
    private val service: YoutubeService,
    private val id: String,
    private val name: String?,
    private val cover: String?,
): Artist {
    private val artistExtractor by lazy { service.getChannelExtractor(id) }

    override fun getUrl(): String = id
    override suspend fun getName(): String {
        if(name != null) return name
        artistExtractor.fetchPage()
        return artistExtractor.name
    }

    override suspend fun getAvatar(): String {
        if(cover != null) return cover
        artistExtractor.fetchPage()
        return artistExtractor.avatarUrl
    }

    override suspend fun getSongs(offset: Int, limit: Int): List<Track> {
        artistExtractor.fetchPage()
        return artistExtractor.initialPage.items
            .filter { it.infoType == InfoItem.InfoType.STREAM }
            .map { service.getTrackClient(it.url, it.name, it.thumbnailUrl) }
    }

    override suspend fun getAlbums(offset: Int, limit: Int): List<Album> {
        artistExtractor.fetchPage()
        return artistExtractor.initialPage.items
            .filter { it.infoType == InfoItem.InfoType.PLAYLIST }
            .map { service.getAlbumClient(it.url, it.name, it.thumbnailUrl) }
    }

    override suspend fun getSingles(offset: Int, limit: Int): List<Album> {
        return emptyList()
//        artistExtractor.fetchPage()
//        return artistExtractor.initialPage.items
//            .filter { it.infoType == InfoItem.InfoType.PLAYLIST }
//            .map { service.getAlbumClient(it.url) }
    }
}