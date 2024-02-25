package tel.jeelpa.musicplayer.soundcloudplugin

import org.schabi.newpipe.extractor.InfoItem
import org.schabi.newpipe.extractor.services.soundcloud.SoundcloudService
import tel.jeelpa.musicplayer.common.models.Album
import tel.jeelpa.musicplayer.common.models.Artist
import tel.jeelpa.musicplayer.common.models.Track

fun SoundcloudService.getArtist(
    id: String,
    name: String? = null,
    avatar: String? = null,
): Artist {
    return SCArtist(this, id, name, avatar)
}

class SCArtist(
    private val service: SoundcloudService,
    private val id: String,
    private val name: String?,
    private val avatar: String?,
): Artist {
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

    override suspend fun getSongs(offset: Int, limit: Int): List<Track> {
        artistExtractor.fetchPage()
        return artistExtractor.initialPage.items
            .filter { it.infoType == InfoItem.InfoType.STREAM }
            .map { service.getTrack(it.url, it.name, it.thumbnailUrl) }
    }

    override suspend fun getAlbums(offset: Int, limit: Int): List<Album> {
        artistExtractor.fetchPage()
        return artistExtractor.initialPage.items
            .filter { it.infoType == InfoItem.InfoType.PLAYLIST }
            .map { service.getAlbum(it.url, it.name, it.thumbnailUrl) }
    }

    override suspend fun getSingles(offset: Int, limit: Int): List<Album> {
        return emptyList()
//        artistExtractor.fetchPage()
//        return artistExtractor.initialPage.items
//            .filter { it.infoType == InfoItem.InfoType.PLAYLIST }
//            .map { service.getAlbumClient(it.url) }
    }
}