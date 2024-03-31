package tel.jeelpa.musicplayer.common.clients

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import tel.jeelpa.musicplayer.common.models.Album

interface AlbumClient {
    fun search(query: String): Flow<PagingData<Album>>

    suspend fun getAlbum(id: String): Album
}