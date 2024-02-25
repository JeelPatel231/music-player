package tel.jeelpa.musicplayer.bandcampplugin

import org.schabi.newpipe.extractor.InfoItem
import org.schabi.newpipe.extractor.services.bandcamp.BandcampService
import tel.jeelpa.musicplayer.common.models.AbstractMediaSource
import tel.jeelpa.musicplayer.common.models.Album
import tel.jeelpa.musicplayer.common.models.Artist
import tel.jeelpa.musicplayer.common.models.StringMediaSource
import tel.jeelpa.musicplayer.common.models.Track

fun BandcampService.getTrackClient(
    id: String,
    name: String? = null,
    cover: String? = null,
): Track {
    return BandcampTrack(this, id, name, cover)
}

class BandcampTrack(
    private val service: BandcampService,
    private val id: String,
    private val name: String?,
    private val cover: String?,
) : Track {
    private val streamExtractor by lazy { service.getStreamExtractor(id) }

    override suspend fun getName(): String {
        if(name != null) return name
        streamExtractor.fetchPage()
        return streamExtractor.name
    }

    override fun getUrl(): String = id

    override suspend fun getMediaSource(): List<AbstractMediaSource> {
        streamExtractor.fetchPage()
        return streamExtractor.audioStreams.map { StringMediaSource(it.content) }
    }

    override suspend fun getRadio(): List<Track> {
        streamExtractor.fetchPage()
        return streamExtractor.relatedItems?.items.orEmpty()
            .filter { it.infoType == InfoItem.InfoType.STREAM }
            .map { service.getTrackClient(it.url, it.name, it.thumbnailUrl) }
    }

    override suspend fun getCover(): String {
        if(cover != null) return cover
        streamExtractor.fetchPage()
        return streamExtractor.thumbnailUrl
    }

    override suspend fun getArtists(): List<Artist> {
        streamExtractor.fetchPage()
        return listOf(streamExtractor.uploaderUrl)
            .map { service.getArtistClient(it) }
    }

    override suspend fun getAlbum(): Album {
        streamExtractor.fetchPage()
        TODO("Not yet implemented")
    }
}