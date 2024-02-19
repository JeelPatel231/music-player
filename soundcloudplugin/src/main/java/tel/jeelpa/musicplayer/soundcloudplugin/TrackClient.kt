package tel.jeelpa.musicplayer.soundcloudplugin

import org.schabi.newpipe.extractor.InfoItem
import org.schabi.newpipe.extractor.services.soundcloud.SoundcloudService
import tel.jeelpa.musicplayer.common.clients.AbstractMediaSource
import tel.jeelpa.musicplayer.common.clients.AlbumClient
import tel.jeelpa.musicplayer.common.clients.ArtistClient
import tel.jeelpa.musicplayer.common.clients.StringMediaSource
import tel.jeelpa.musicplayer.common.clients.TrackClient

fun SoundcloudService.getTrackClient(
    id: String,
    name: String? = null,
    cover: String? = null
): TrackClient {
    return SCTrackClient(this, id, name, cover)
}

class SCTrackClient(
    private val service: SoundcloudService,
    private val id: String,
    private val name: String?,
    private val cover: String?,
) : TrackClient {
    private val streamExtractor by lazy { service.getStreamExtractor(id) }

    override fun getName(): String {
        if(name != null) return name
        streamExtractor.fetchPage()
        return streamExtractor.name
    }

    override fun getUrl(): String = id

    override fun getMediaSource(): List<AbstractMediaSource> {
        streamExtractor.fetchPage()
        return streamExtractor.audioStreams.map { StringMediaSource(it.content) }
    }

    override fun getRadio(): List<TrackClient> {
        streamExtractor.fetchPage()
        return streamExtractor.relatedItems?.items.orEmpty()
            .filter { it.infoType == InfoItem.InfoType.STREAM }
            .map { service.getTrackClient(it.url, it.name, it.thumbnailUrl) }
    }

    override fun getCover(): String {
        if(cover != null) return cover
        streamExtractor.fetchPage()
        return streamExtractor.thumbnailUrl
    }

    override fun getArtists(): List<ArtistClient> {
        streamExtractor.fetchPage()
        return listOf(streamExtractor.uploaderUrl)
            .map { service.getArtistClient(it, streamExtractor.uploaderName, streamExtractor.uploaderAvatarUrl) }
    }

    override fun getAlbum(): AlbumClient {
        streamExtractor.fetchPage()
        TODO("Not yet implemented")
    }
}