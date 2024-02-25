package tel.jeelpa.musicplayer.common.clients

import tel.jeelpa.musicplayer.common.models.Track

interface TrackClient {
    fun search(query: String, page: Int, perPage: Int): List<Track>
}