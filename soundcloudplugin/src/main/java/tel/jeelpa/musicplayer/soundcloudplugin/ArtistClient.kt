package tel.jeelpa.musicplayer.soundcloudplugin

import org.schabi.newpipe.extractor.InfoItem
import org.schabi.newpipe.extractor.services.soundcloud.SoundcloudService
import tel.jeelpa.musicplayer.common.clients.AlbumClient
import tel.jeelpa.musicplayer.common.clients.ArtistClient
import tel.jeelpa.musicplayer.common.clients.TrackClient

fun SoundcloudService.getArtistClient(
    id: String,
    name: String? = null,
    avatar: String? = null,
): ArtistClient {
    return SCArtistClient(this, id, name, avatar)
}

class SCArtistClient(
    private val service: SoundcloudService,
    private val id: String,
    private val name: String?,
    private val avatar: String?,
): ArtistClient {
    private val artistExtractor by lazy { service.getChannelExtractor(id) }

    override fun getUrl(): String = id

    override suspend fun getName(): String {
        if (name != null) return name
        artistExtractor.fetchPage()
        return artistExtractor.name
    }

    override suspend fun getAvatar(): String {
        if(avatar != null) return avatar
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