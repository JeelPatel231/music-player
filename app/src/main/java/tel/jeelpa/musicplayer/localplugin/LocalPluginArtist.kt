package tel.jeelpa.musicplayer.localplugin

import android.net.Uri
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import tel.jeelpa.musicplayer.common.models.Album
import tel.jeelpa.musicplayer.common.models.Artist
import tel.jeelpa.musicplayer.common.models.Track
import tel.jeelpa.musicplayer.localplugin.content.LocalPluginContentResolver
import tel.jeelpa.musicplayer.ui.utils.toPagedData

class LocalPluginArtist(
    private val resolver: LocalPluginContentResolver,
    private val id: Long,
    private val name: String,
    private val avatar: Uri?
): Artist {
    override fun getUrl(): String = id.toString()

    override suspend fun getName(): String = name

    override suspend fun getAvatar(): String? = avatar?.toString()

    override suspend fun getSongs(): Flow<PagingData<Track>> {
        return flowOf(resolver.trackResolver.getByArtist(id.toString()).toPagedData())
    }

    override suspend fun getAlbums(): Flow<PagingData<Album>> {
        return flowOf(resolver.albumResolver.getByArtist(id.toString()).toPagedData())
    }

    override suspend fun getSingles(): Flow<PagingData<Album>> {
        TODO("Not yet implemented")
    }
}