package tel.jeelpa.musicplayer.soundcloudplugin.clients

import tel.jeelpa.musicplayer.common.clients.TrackClient
import tel.jeelpa.musicplayer.common.models.Track


class SCTrackClient: TrackClient {

    override fun search(query: String, page: Int, perPage: Int): List<Track> {
        TODO("Not yet implemented")
    }
}
