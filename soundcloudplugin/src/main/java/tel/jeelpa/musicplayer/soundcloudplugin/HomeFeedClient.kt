package tel.jeelpa.musicplayer.soundcloudplugin

import org.schabi.newpipe.extractor.InfoItem
import org.schabi.newpipe.extractor.services.soundcloud.SoundcloudService
import tel.jeelpa.musicplayer.common.clients.HomeFeedClient
import tel.jeelpa.musicplayer.common.models.Album
import tel.jeelpa.musicplayer.common.models.Artist
import tel.jeelpa.musicplayer.common.models.Track

fun SoundcloudService.getHomeFeedClient(): SCHomeFeedClient {
    return SCHomeFeedClient(this)
}

class SCHomeFeedClient(
    private val service: SoundcloudService,
): HomeFeedClient {
    private val feedExtractor by lazy { service.getPlaylistExtractor("https://soundcloud.com/trending-music-in/sets/folk") } // .kioskList.getExtractorByUrl("https://soundcloud.com/trending-music-in/sets/folk", null) }
//    private val feedExtractor by lazy { service.kioskList.getExtractorByUrl("https://soundcloud.com/charts/top", null) }

    override suspend fun getSongs(offset: Int, limit: Int): List<Track> {
        feedExtractor.fetchPage()
        return feedExtractor.initialPage.items.filter {
            it.infoType == InfoItem.InfoType.STREAM
        }.map {
            service.getTrack(it.url, it.name, it.thumbnailUrl)
        }
    }

    override suspend fun getAlbums(offset: Int, limit: Int): List<Album> {
        feedExtractor.fetchPage()
        return feedExtractor.initialPage.items.filter {
            it.infoType == InfoItem.InfoType.PLAYLIST
        }.map {
            service.getAlbum(it.url, it.name, it.thumbnailUrl)
        }
    }

    override suspend fun getArtists(count: Int, limit: Int): List<Artist> {
        feedExtractor.fetchPage()
        return feedExtractor.initialPage.items.filter {
            it.infoType == InfoItem.InfoType.CHANNEL
        }.map {
            service.getArtist(it.url, it.name, it.thumbnailUrl)
        }
    }
}