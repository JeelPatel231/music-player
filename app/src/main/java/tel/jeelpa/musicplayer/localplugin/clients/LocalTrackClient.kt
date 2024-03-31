package tel.jeelpa.musicplayer.localplugin.clients

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import tel.jeelpa.musicplayer.common.clients.TrackClient
import tel.jeelpa.musicplayer.common.models.Track
import tel.jeelpa.musicplayer.localplugin.content.TrackResolver
import tel.jeelpa.musicplayer.ui.utils.toPagedData

class LocalTrackClient(
    private val trackResolver: TrackResolver
): TrackClient {
    override fun search(query: String): Flow<PagingData<Track>> {
        // TODO: use LIMIT and OFFSET for pagination
        return flowOf(trackResolver.search(query).toPagedData())
    }

    override suspend fun getTrack(id: String): Track {
        return trackResolver.getById(id)
    }

}