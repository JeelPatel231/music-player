package tel.jeelpa.musicplayer.soundcloudplugin.clients

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.schabi.newpipe.extractor.StreamingService
import tel.jeelpa.musicplayer.common.clients.ArtistClient
import tel.jeelpa.musicplayer.common.models.Artist
import tel.jeelpa.musicplayer.soundcloudplugin.getArtist

class NPArtistClient(
    private val service: StreamingService
) : ArtistClient {
    override fun search(query: String): Flow<PagingData<Artist>> {
        TODO("Not yet implemented")
    }

    override suspend fun getArtist(id: String): Artist =
        service.getArtist(id)
}