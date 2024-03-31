package tel.jeelpa.musicplayer.common.models

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

interface Track: DisplayableModel {
    fun getUrl(): String
    suspend fun getName(): String
    suspend fun getMediaSource(): List<AbstractMediaSource>
    suspend fun getRadio(): Flow<PagingData<Track>>
    suspend fun getCover(): String

    suspend fun getArtists():  List<Artist>
    suspend fun getAlbum(): Album
}
