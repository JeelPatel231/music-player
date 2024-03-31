package tel.jeelpa.musicplayer.localplugin.clients

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import tel.jeelpa.musicplayer.common.clients.AlbumClient
import tel.jeelpa.musicplayer.common.models.Album
import tel.jeelpa.musicplayer.localplugin.content.AlbumResolver
import tel.jeelpa.musicplayer.ui.utils.toPagedData

class LocalAlbumClient(
    private val albumResolver: AlbumResolver
): AlbumClient {
    override fun search(query: String): Flow<PagingData<Album>> {
        // TODO: use LIMIT and OFFSET for pagination
        return flowOf(albumResolver.search(query).toPagedData())
    }

    override suspend fun getAlbum(id: String): Album {
        return albumResolver.getById(id)
    }
}