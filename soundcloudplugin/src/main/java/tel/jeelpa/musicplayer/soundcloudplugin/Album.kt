package tel.jeelpa.musicplayer.soundcloudplugin

import org.schabi.newpipe.extractor.services.soundcloud.SoundcloudService
import org.schabi.newpipe.extractor.stream.StreamInfoItem
import tel.jeelpa.musicplayer.common.models.Album
import tel.jeelpa.musicplayer.common.models.Artist
import tel.jeelpa.musicplayer.common.models.Track

fun SoundcloudService.getAlbum(
    id: String,
    name: String? = null,
    cover: String? = null,
): SCAlbum {
    return SCAlbum(this, id, name, cover)
}

class SCAlbum(
    private val service: SoundcloudService,
    private val id: String,
    private val name: String?,
    private val cover: String?,
): Album {
    private val albumExtractor by lazy { service.getPlaylistExtractor(id) }

    override fun getUrl(): String = id

    override suspend fun getName(): String {
        if(name != null) return name
        albumExtractor.fetchPage()
        return albumExtractor.name
    }

    override suspend fun getAlbumArtists(): List<Artist> {
        albumExtractor.fetchPage()
        return listOf(albumExtractor.uploaderUrl)
            .map { service.getArtist(it, albumExtractor.uploaderName, albumExtractor.uploaderAvatarUrl) }
    }

    override suspend fun getAlbumArt(): String {
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


    override suspend fun getSongs(offset: Int, limit: Int): List<Track> {
        return fetchAllPages()
            .map { service.getTrack(it.url, it.name, it.thumbnailUrl) }
    }
}