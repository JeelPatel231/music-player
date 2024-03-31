package tel.jeelpa.musicplayer.common.clients

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import tel.jeelpa.musicplayer.common.models.Track

interface TrackClient {
    fun search(query: String): Flow<PagingData<Track>>

    suspend fun getTrack(id: String): Track
}