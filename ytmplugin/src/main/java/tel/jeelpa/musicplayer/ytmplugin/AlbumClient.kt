package tel.jeelpa.musicplayer.ytmplugin

import org.schabi.newpipe.extractor.services.youtube.YoutubeService
import tel.jeelpa.musicplayer.common.models.Album
import tel.jeelpa.musicplayer.common.models.Artist
import tel.jeelpa.musicplayer.common.models.Track

fun YoutubeService.getAlbumClient(
    id: String,
    name: String? = null,
    cover: String? = null,
): YTMAlbum {
    return YTMAlbum(this, id, name, cover)
}

class YTMAlbum(
    private val service: YoutubeService,
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
            .map { service.getArtistClient(it, albumExtractor.name, albumExtractor.thumbnailUrl) }
    }

    override suspend fun getAlbumArt(): String {
        if(cover != null) return cover
        albumExtractor.fetchPage()
        return albumExtractor.thumbnailUrl
    }

    override suspend fun getSongs(offset: Int, limit: Int): List<Track> {
        albumExtractor.fetchPage()
        val page = if (offset == 0) {
            albumExtractor.initialPage
        } else  {
            TODO()
//            albumExtractor.getPage(Page(id, nextPageId))
        }

        return page.items.map { service.getTrackClient(it.url, it.name, it.thumbnailUrl) }
    }
}