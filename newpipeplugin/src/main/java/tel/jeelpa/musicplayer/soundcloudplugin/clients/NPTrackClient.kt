package tel.jeelpa.musicplayer.soundcloudplugin.clients

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.schabi.newpipe.extractor.StreamingService
import tel.jeelpa.musicplayer.common.clients.TrackClient
import tel.jeelpa.musicplayer.common.models.Track
import tel.jeelpa.musicplayer.soundcloudplugin.getTrack


class NPTrackClient(
    private val service: StreamingService
): TrackClient {

    override fun search(query: String): Flow<PagingData<Track>> {
        TODO()
//        val searchExtractor = service.getSearchExtractor(query)
//        searchExtractor.fetchPage()
//        return searchExtractor.initialPage.items
//            .also { println(it) }
//            .filter {it.infoType == InfoItem.InfoType.STREAM }
//            .also { println(it) }
//            .map { service.getTrack(it.url, it.name, it.thumbnailUrl) }
//            .also { println(it) }
    }

    override suspend fun getTrack(id: String): Track =
        service.getTrack(id)
}
