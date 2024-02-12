package tel.jeelpa.musicplayer.soundcloudplugin

import org.schabi.newpipe.extractor.services.soundcloud.SoundcloudService
import org.schabi.newpipe.extractor.services.youtube.YoutubeService
import tel.jeelpa.musicplayer.common.clients.AlbumClient
import tel.jeelpa.musicplayer.common.clients.ArtistClient
import tel.jeelpa.musicplayer.common.clients.HomeFeedClient
import tel.jeelpa.musicplayer.common.clients.TrackClient

fun SoundcloudService.getHomeFeedClient(): SCHomeFeedClient {
    return SCHomeFeedClient(this)
}

class SCHomeFeedClient(
    private val service: SoundcloudService,
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