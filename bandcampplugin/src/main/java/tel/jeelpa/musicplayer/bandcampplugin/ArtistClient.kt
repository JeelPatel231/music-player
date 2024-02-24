package tel.jeelpa.musicplayer.bandcampplugin

import org.schabi.newpipe.extractor.InfoItem
import org.schabi.newpipe.extractor.services.bandcamp.BandcampService
import tel.jeelpa.musicplayer.common.clients.AlbumClient
import tel.jeelpa.musicplayer.common.clients.ArtistClient
import tel.jeelpa.musicplayer.common.clients.TrackClient

fun BandcampService.getArtistClient(
    id: String,
    name: String? = null,
    cover: String? = null,
): ArtistClient {
    return BandcampArtistClient(this, id, name, cover)
}

class BandcampArtistClient(
    private val service: BandcampService,
    private val id: String,
    private val name: String?,
    private val cover: String?,
): ArtistClient {
    private val artistExtractor by lazy { service.getChannelExtractor(id) }

    override fun getUrl(): String = id
    override suspend fun getName(): String {
        if(name != null) return name
        artistExtractor.fetchPage()
        return artistExtractor.name
    }

    override suspend fun getAvatar(): String {
        if(cover != null) return cover
        artistExtractor.fetchPage()
        return artistExtractor.avatarUrl
    }

    override suspend fun getSongs(offset: Int, limit: Int): List<TrackClient> {
        artistExtractor.fetchPage()
        return artistExtractor.initialPage.items
            .filter { it.infoType == InfoItem.InfoType.STREAM }
            .map { service.getTrackClient(it.url, it.name, it.thumbnailUrl) }
    }

    override suspend fun getAlbums(offset: Int, limit: Int): List<AlbumClient> {
        artistExtractor.fetchPage()
        return artistExtractor.initialPage.items
            .filter { it.infoType == InfoItem.InfoType.PLAYLIST }
            .map { service.getAlbumClient(it.url, it.name, it.thumbnailUrl) }
    }

    override suspend fun getSingles(offset: Int, limit: Int): List<AlbumClient> {
        return emptyList()
//        artistExtractor.fetchPage()
//        return artistExtractor.initialPage.items
//            .filter { it.infoType == InfoItem.InfoType.PLAYLIST }
//            .map { service.getAlbumClient(it.url) }
    }
}