package tel.jeelpa.musicplayer.soundcloudplugin

import org.schabi.newpipe.extractor.InfoItem
import org.schabi.newpipe.extractor.services.soundcloud.SoundcloudService
import org.schabi.newpipe.extractor.services.youtube.YoutubeService
import tel.jeelpa.musicplayer.common.clients.AbstractMediaSource
import tel.jeelpa.musicplayer.common.clients.AlbumClient
import tel.jeelpa.musicplayer.common.clients.ArtistClient
import tel.jeelpa.musicplayer.common.clients.StringMediaSource
import tel.jeelpa.musicplayer.common.clients.TrackClient

fun SoundcloudService.getTrackClient(id: String): TrackClient {
    return SCTrackClient(this, id)
}

class SCTrackClient(
    private val service: SoundcloudService,
    private val id: String,
) : TrackClient {
    private val streamExtractor = service.getStreamExtractor(id)

    override fun getName(): String {
        streamExtractor.fetchPage()
        return streamExtractor.name
    }

    override fun getMediaSource(): List<AbstractMediaSource> {
        streamExtractor.fetchPage()
        return streamExtractor.audioStreams.map { StringMediaSource(it.content) }
    }

    override fun getRadio(): List<TrackClient> {
        streamExtractor.fetchPage()
        return streamExtractor.relatedItems?.items.orEmpty()
            .filter { it.infoType == InfoItem.InfoType.STREAM }
            .map { service.getTrackClient(it.url) }
    }

    override fun getCover(): String {
        streamExtractor.fetchPage()
        return streamExtractor.thumbnailUrl
    }

    override fun getArtists(): List<ArtistClient> {
        streamExtractor.fetchPage()
        return listOf(streamExtractor.uploaderUrl)
            .map { service.getArtistClient(it) }
    }

    override fun getAlbum(): AlbumClient {
        streamExtractor.fetchPage()
        TODO("Not yet implemented")
    }
}