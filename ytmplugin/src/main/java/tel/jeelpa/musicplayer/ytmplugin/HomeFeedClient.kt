package tel.jeelpa.musicplayer.ytmplugin

import org.schabi.newpipe.extractor.StreamingService
import org.schabi.newpipe.extractor.services.youtube.YoutubeService
import tel.jeelpa.musicplayer.common.clients.AlbumClient
import tel.jeelpa.musicplayer.common.clients.ArtistClient
import tel.jeelpa.musicplayer.common.clients.HomeFeedClient
import tel.jeelpa.musicplayer.common.clients.TrackClient

fun YoutubeService.getHomeFeedClient(): YTMHomeFeedClient {
    return YTMHomeFeedClient(this)
}

class YTMHomeFeedClient(
    private val service: YoutubeService,
): HomeFeedClient {
    private val feedExtractor = service.getFeedExtractor("TODO: CHANGE")!!

    override fun getSongs(offset: Int, limit: Int): List<TrackClient> {
        feedExtractor.fetchPage()
        TODO("Not yet implemented")
    }

    override fun getAlbums(offset: Int, limit: Int): List<AlbumClient> {
        feedExtractor.fetchPage()
        TODO("Not yet implemented")
    }

    override fun getArtists(count: Int, limit: Int): List<ArtistClient> {
        feedExtractor.fetchPage()
        TODO("Not yet implemented")
    }
}