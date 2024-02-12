package tel.jeelpa.musicplayer.soundcloudplugin

import org.schabi.newpipe.extractor.InfoItem
import org.schabi.newpipe.extractor.services.soundcloud.SoundcloudService
import org.schabi.newpipe.extractor.services.youtube.YoutubeService
import tel.jeelpa.musicplayer.common.clients.AlbumClient
import tel.jeelpa.musicplayer.common.clients.ArtistClient
import tel.jeelpa.musicplayer.common.clients.TrackClient

fun SoundcloudService.getArtistClient(id: String): ArtistClient {
    return SCArtistClient(this, id)
}

class SCArtistClient(
    private val service: SoundcloudService,
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