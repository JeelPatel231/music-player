package tel.jeelpa.musicplayer.bandcampplugin

import org.schabi.newpipe.extractor.InfoItem
import org.schabi.newpipe.extractor.services.bandcamp.BandcampService
import tel.jeelpa.musicplayer.common.models.Album
import tel.jeelpa.musicplayer.common.models.Artist
import tel.jeelpa.musicplayer.common.clients.HomeFeedClient
import tel.jeelpa.musicplayer.common.models.Track

fun BandcampService.getHomeFeedClient(): BandcampHomeFeedClient {
    return BandcampHomeFeedClient(this)
}

class BandcampHomeFeedClient(
    private val service: BandcampService,
): HomeFeedClient {

    private val feedExtractor by lazy { service.kioskList.defaultKioskExtractor }

    override suspend fun getSongs(offset: Int, limit: Int): List<Track> {
        feedExtractor.fetchPage()
        return feedExtractor.initialPage.items.filter {
            it.infoType == InfoItem.InfoType.STREAM
        }.map {
            service.getTrackClient(it.url, it.name, it.thumbnailUrl)
        }
    }

    override suspend fun getAlbums(offset: Int, limit: Int): List<Album> {
        feedExtractor.fetchPage()
        return feedExtractor.initialPage.items.filter {
            it.infoType == InfoItem.InfoType.PLAYLIST
        }.map {
            service.getAlbumClient(it.url, it.name, it.thumbnailUrl)
        }
    }

    override suspend fun getArtists(count: Int, limit: Int): List<Artist> {
        feedExtractor.fetchPage()
        return feedExtractor.initialPage.items.filter {
            it.infoType == InfoItem.InfoType.CHANNEL
        }.map {
            service.getArtistClient(it.url, it.name, it.thumbnailUrl)
        }
    }
}
