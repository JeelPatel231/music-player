package tel.jeelpa.musicplayer.ytmplugin

import org.schabi.newpipe.extractor.Page
import org.schabi.newpipe.extractor.ServiceList.YouTube
import org.schabi.newpipe.extractor.StreamingService
import org.schabi.newpipe.extractor.services.youtube.YoutubeService
import tel.jeelpa.musicplayer.common.clients.AlbumClient
import tel.jeelpa.musicplayer.common.clients.ArtistClient
import tel.jeelpa.musicplayer.common.clients.TrackClient

fun YoutubeService.getAlbumClient(id: String): YTMAlbumClient {
    return YTMAlbumClient(this, id)
}

class YTMAlbumClient(
    private val service: YoutubeService,
    private val id: String,
): AlbumClient {
    private val albumExtractor = service.getPlaylistExtractor(id)

    override fun getName(): String {
        albumExtractor.fetchPage()
        return albumExtractor.name
    }

    override fun getAlbumArtists(): List<ArtistClient> {
        albumExtractor.fetchPage()
        return listOf(albumExtractor.uploaderUrl)
            .map { service.getArtistClient(it) }
    }

    override fun getAlbumArt(): String {
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

        return page.items.map { service.getTrackClient(it.url) }
    }
}