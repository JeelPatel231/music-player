package tel.jeelpa.musicplayer.common.clients

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import tel.jeelpa.musicplayer.common.models.Artist

interface ArtistClient {
    fun search(query: String): Flow<PagingData<Artist>>

    suspend fun getArtist(id: String): Artist
}