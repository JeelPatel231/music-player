package tel.jeelpa.musicplayer.ytmplugin

import org.schabi.newpipe.extractor.services.youtube.YoutubeService
import tel.jeelpa.musicplayer.common.models.Album
import tel.jeelpa.musicplayer.common.models.Artist
import tel.jeelpa.musicplayer.common.clients.HomeFeedClient
import tel.jeelpa.musicplayer.common.models.Track

fun YoutubeService.getHomeFeedClient(): YTMHomeFeedClient {
    return YTMHomeFeedClient(this)
}

class YTMHomeFeedClient(
    private val service: YoutubeService,
): HomeFeedClient {
    private val feedExtractor by lazy { service.getFeedExtractor("TODO: CHANGE") }

    override suspend fun getSongs(offset: Int, limit: Int): List<Track> {
        feedExtractor.fetchPage()
        TODO("Not yet implemented")
    }

    override suspend fun getAlbums(offset: Int, limit: Int): List<Album> {
        feedExtractor.fetchPage()
        TODO("Not yet implemented")
    }

    override suspend fun getArtists(count: Int, limit: Int): List<Artist> {
        feedExtractor.fetchPage()
        TODO("Not yet implemented")
    }
}
