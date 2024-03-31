package tel.jeelpa.musicplayer.common.models

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

interface Album: DisplayableModel {
    fun getUrl(): String
    suspend fun getName(): String
    suspend fun getAlbumArtists(): List<Artist>
    suspend fun getAlbumArt(): String
    suspend fun getSongs(): Flow<PagingData<Track>>
}
