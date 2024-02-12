package tel.jeelpa.musicplayer.ytmplugin

import org.schabi.newpipe.extractor.InfoItem
import org.schabi.newpipe.extractor.StreamingService
import org.schabi.newpipe.extractor.services.youtube.YoutubeService
import tel.jeelpa.musicplayer.common.clients.AlbumClient
import tel.jeelpa.musicplayer.common.clients.ArtistClient
import tel.jeelpa.musicplayer.common.clients.TrackClient

fun YoutubeService.getArtistClient(id: String): ArtistClient {
    return YTMArtistClient(this, id)
}

class YTMArtistClient(
    private val service: YoutubeService,
    private val id: String
): ArtistClient {
    private val artistExtractor = service.getChannelExtractor(id)

    override fun getAvatar(): String {
        artistExtractor.fetchPage()
        return artistExtractor.avatarUrl
    }

    override fun getSongs(offset: Int, limit: Int): List<TrackClient> {
        artistExtractor.fetchPage()
        return artistExtractor.initialPage.items
            .filter { it.infoType == InfoItem.InfoType.STREAM }
            .map { service.getTrackClient(it.url) }
    }

    override fun getAlbums(offset: Int, limit: Int): List<AlbumClient> {
        artistExtractor.fetchPage()
        return artistExtractor.initialPage.items
            .filter { it.infoType == InfoItem.InfoType.PLAYLIST }
            .map { service.getAlbumClient(it.url) }
    }

    override fun getSingles(offset: Int, limit: Int): List<AlbumClient> {
        return emptyList()
//        artistExtractor.fetchPage()
//        return artistExtractor.initialPage.items
//            .filter { it.infoType == InfoItem.InfoType.PLAYLIST }
//            .map { service.getAlbumClient(it.url) }
    }
}