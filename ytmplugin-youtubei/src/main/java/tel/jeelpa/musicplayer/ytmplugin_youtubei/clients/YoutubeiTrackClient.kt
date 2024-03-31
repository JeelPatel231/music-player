package tel.jeelpa.musicplayer.ytmplugin_youtubei.clients

import androidx.paging.PagingData
import dev.toastbits.ytmkt.impl.youtubei.YoutubeiApi
import kotlinx.coroutines.flow.Flow
import tel.jeelpa.musicplayer.common.clients.TrackClient
import tel.jeelpa.musicplayer.common.models.Track
import tel.jeelpa.musicplayer.ytmplugin_youtubei.toTrack

class YoutubeiTrackClient(
    private val api: YoutubeiApi
): TrackClient {
    override fun search(query: String): Flow<PagingData<Track>> {
        TODO("Not yet implemented")
    }

    override suspend fun getTrack(id: String): Track =
        api.LoadSong.loadSong(id).getOrThrow().toTrack(api)

}