package tel.jeelpa.musicplayer.soundcloudplugin.clients

import tel.jeelpa.musicplayer.common.clients.ArtistClient
import tel.jeelpa.musicplayer.common.models.Artist

class SCArtistClient : ArtistClient {
    override fun search(query: String, page: Int, perPage: Int): List<Artist> {
        TODO("Not yet implemented")
    }
}