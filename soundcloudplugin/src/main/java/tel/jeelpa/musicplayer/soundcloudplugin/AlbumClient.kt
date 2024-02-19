package tel.jeelpa.musicplayer.soundcloudplugin

import org.schabi.newpipe.extractor.services.soundcloud.SoundcloudService
import org.schabi.newpipe.extractor.stream.StreamInfoItem
import tel.jeelpa.musicplayer.common.clients.AlbumClient
import tel.jeelpa.musicplayer.common.clients.ArtistClient
import tel.jeelpa.musicplayer.common.clients.TrackClient

fun SoundcloudService.getAlbumClient(
    id: String,
    name: String? = null,
    cover: String? = null,
): SCAlbumClient {
    return SCAlbumClient(this, id, name, cover)
}

class SCAlbumClient(
    private val service: SoundcloudService,
    private val id: String,
    private val name: String?,
    private val cover: String?,
): AlbumClient {
    private val albumExtractor by lazy { service.getPlaylistExtractor(id) }

    override fun getUrl(): String = id

    override fun getName(): String {
        if(name != null) return name
        albumExtractor.fetchPage()
        return albumExtractor.name
    }

    override fun getAlbumArtists(): List<ArtistClient> {
        albumExtractor.fetchPage()
        return listOf(albumExtractor.uploaderUrl)
            .map { service.getArtistClient(it, albumExtractor.uploaderName, albumExtractor.uploaderAvatarUrl) }
    }

    override fun getAlbumArt(): String {
        if(cover != null) return cover
        albumExtractor.fetchPage()
        return albumExtractor.thumbnailUrl
    }

    private fun fetchAllPages(): List<StreamInfoItem> {
        albumExtractor.fetchPage()
        var page = albumExtractor.initialPage
        val acc = mutableListOf<StreamInfoItem>()
        acc.addAll(page.items)
        while (page.hasNextPage()) {
            page = albumExtractor.getPage(page.nextPage)
            acc.addAll(page.items)
        }

        return acc
    }


    override fun getSongs(offset: Int, limit: Int): List<TrackClient> {
        return fetchAllPages()
            .onEach { println( it.name )}
            .map { service.getTrackClient(it.url, it.name, it.thumbnailUrl) }
    }
}