package tel.jeelpa.musicplayer.soundcloudplugin.clients

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.schabi.newpipe.extractor.StreamingService
import tel.jeelpa.musicplayer.common.clients.AlbumClient
import tel.jeelpa.musicplayer.common.models.Album
import tel.jeelpa.musicplayer.soundcloudplugin.getAlbum

class NPAlbumClient(
    private val service: StreamingService
): AlbumClient {
    override fun search(query: String): Flow<PagingData<Album>> {
        TODO("Not yet implemented")
    }

    override suspend fun getAlbum(id: String): Album  =
        service.getAlbum(id)
}