package tel.jeelpa.musicplayer.common.models

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

interface Artist: DisplayableModel {
    fun getUrl(): String
    suspend fun getName(): String
    suspend fun getAvatar(): String?
    suspend fun getSongs(): Flow<PagingData<Track>>
    suspend fun getAlbums(): Flow<PagingData<Album>>
    suspend fun getSingles(): Flow<PagingData<Album>>
}
