package tel.jeelpa.musicplayer.ytmplugin_youtubei.clients

import androidx.paging.PagingData
import dev.toastbits.ytmkt.impl.youtubei.YoutubeiApi
import kotlinx.coroutines.flow.Flow
import tel.jeelpa.musicplayer.common.clients.AlbumClient
import tel.jeelpa.musicplayer.common.models.Album
import tel.jeelpa.musicplayer.ytmplugin_youtubei.toAlbum

class YoutubeiAlbumClient(
    private val api: YoutubeiApi
) : AlbumClient {
    override fun search(query: String): Flow<PagingData<Album>> {
        TODO("Not yet implemented")
    }

    override suspend fun getAlbum(id: String): Album =
        api.LoadPlaylist.loadPlaylist(id).getOrThrow().toAlbum(api)

}
