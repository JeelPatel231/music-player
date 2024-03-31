package tel.jeelpa.musicplayer.localplugin.clients

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import tel.jeelpa.musicplayer.common.clients.ArtistClient
import tel.jeelpa.musicplayer.common.models.Artist
import tel.jeelpa.musicplayer.localplugin.content.ArtistResolver
import tel.jeelpa.musicplayer.ui.utils.toPagedData

class LocalArtistClient(
    private val artistResolver: ArtistResolver
): ArtistClient {
    override fun search(query: String): Flow<PagingData<Artist>> {
        // TODO: use LIMIT and OFFSET for pagination
        return flowOf(artistResolver.search(query).toPagedData())
    }

    override suspend fun getArtist(id: String): Artist {
        return artistResolver.getById(id)
    }
}