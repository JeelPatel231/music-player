package tel.jeelpa.musicplayer.bandcampplugin

import org.schabi.newpipe.extractor.services.bandcamp.BandcampService
import tel.jeelpa.musicplayer.common.clients.AlbumClient
import tel.jeelpa.musicplayer.common.clients.ArtistClient
import tel.jeelpa.musicplayer.common.clients.TrackClient

fun BandcampService.getAlbumClient(
    id: String,
    name: String? = null,
    cover: String? = null,
): BandcampAlbumClient {
    return BandcampAlbumClient(this, id, name, cover)
}

class BandcampAlbumClient(
    private val service: BandcampService,
    private val id: String,
    private val name: String?,
    private val cover: String?,
): AlbumClient {
    private val albumExtractor by lazy { service.getPlaylistExtractor(id) }

    override fun getUrl(): String = id

    override suspend fun getName(): String {
        if(name != null) return name
        albumExtractor.fetchPage()
        return albumExtractor.name
    }

    override suspend fun getAlbumArtists(): List<ArtistClient> {
        albumExtractor.fetchPage()
        return listOf(albumExtractor.uploaderUrl)
            .map { service.getArtistClient(it, albumExtractor.name, albumExtractor.thumbnailUrl) }
    }

    override suspend fun getAlbumArt(): String {
        if(cover != null) return cover
        albumExtractor.fetchPage()
        return albumExtractor.thumbnailUrl
    }

    override suspend fun getSongs(offset: Int, limit: Int): List<TrackClient> {
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