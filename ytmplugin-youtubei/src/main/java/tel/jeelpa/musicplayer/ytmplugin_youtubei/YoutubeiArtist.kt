package tel.jeelpa.musicplayer.ytmplugin_youtubei

import androidx.paging.PagingData
import dev.toastbits.ytmkt.impl.youtubei.YoutubeiApi
import kotlinx.coroutines.flow.Flow
import tel.jeelpa.musicplayer.common.models.Album
import tel.jeelpa.musicplayer.common.models.Artist
import tel.jeelpa.musicplayer.common.models.Track

fun YoutubeiApi.getArtist(id: String, name: String, thumbnail: String?): YoutubeiArtist {
    return YoutubeiArtist(this, id, name, thumbnail)
}

class YoutubeiArtist(
    private val api: YoutubeiApi,
    private val id: String,
    private val name: String,
    private val thumbnail: String?,
): Artist {
    override fun getUrl(): String = id

    override suspend fun getName(): String = name

    override suspend fun getAvatar(): String? = thumbnail

    override suspend fun getSongs(): Flow<PagingData<Track>> {
        TODO("Not yet implemented")
    }

    override suspend fun getAlbums(): Flow<PagingData<Album>> {
        TODO("Not yet implemented")
    }

    override suspend fun getSingles(): Flow<PagingData<Album>> {
        TODO("Not yet implemented")
    }
}