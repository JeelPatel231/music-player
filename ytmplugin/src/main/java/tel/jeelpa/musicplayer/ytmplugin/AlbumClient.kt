package tel.jeelpa.musicplayer.ytmplugin

import org.schabi.newpipe.extractor.services.youtube.YoutubeService
import tel.jeelpa.musicplayer.common.clients.AlbumClient
import tel.jeelpa.musicplayer.common.clients.ArtistClient
import tel.jeelpa.musicplayer.common.clients.TrackClient

fun YoutubeService.getAlbumClient(
    id: String,
    name: String? = null,
    cover: String? = null,
): YTMAlbumClient {
    return YTMAlbumClient(this, id, name, cover)
}

class YTMAlbumClient(
    private val service: YoutubeService,
    private val id: String,
    private val name: String?,
    private val cover: String?,
): AlbumClient {
    private val albumExtractor by lazy { service.getPlaylistExtractor(id) }

    override fun getUrl(): String = id

    override fun getName(): String {
        if(name != null) return name
        albumExtractor.fetchPage()
        return albumExtractor.name
    }

    override fun getAlbumArtists(): List<ArtistClient> {
        albumExtractor.fetchPage()
        return listOf(albumExtractor.uploaderUrl)
            .map { service.getArtistClient(it, albumExtractor.name, albumExtractor.thumbnailUrl) }
    }

    override fun getAlbumArt(): String {
        if(cover != null) return cover
        albumExtractor.fetchPage()
        return albumExtractor.thumbnailUrl
    }

    override fun getSongs(offset: Int, limit: Int): List<TrackClient> {
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