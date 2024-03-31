package tel.jeelpa.musicplayer.ytmplugin_youtubei.clients

import androidx.paging.PagingData
import dev.toastbits.ytmkt.impl.youtubei.YoutubeiApi
import kotlinx.coroutines.flow.Flow
import tel.jeelpa.musicplayer.common.clients.ArtistClient
import tel.jeelpa.musicplayer.common.models.Artist
import tel.jeelpa.musicplayer.ytmplugin_youtubei.toArtist

class YoutubeiArtistClient(
    private val api: YoutubeiApi
): ArtistClient {
    override fun search(query: String): Flow<PagingData<Artist>> {
        TODO("Not yet implemented")
    }

    override suspend fun getArtist(id: String): Artist =
        api.LoadArtist.loadArtist(id).getOrThrow().toArtist(api)
}