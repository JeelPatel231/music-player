package tel.jeelpa.musicplayer.localplugin.clients

import tel.jeelpa.musicplayer.common.clients.TrackClient
import tel.jeelpa.musicplayer.common.models.Track
import tel.jeelpa.musicplayer.localplugin.content.TrackResolver

class LocalTrackClient(
    private val trackResolver: TrackResolver
): TrackClient {
    override fun search(query: String, page: Int, perPage: Int): List<Track> {
        if (page != 0 || perPage != 0) TODO()
        return trackResolver.search(query)
    }
}