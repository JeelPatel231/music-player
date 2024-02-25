package tel.jeelpa.musicplayer.common.clients

import tel.jeelpa.musicplayer.common.models.Artist

interface ArtistClient {
    fun search(query: String, page: Int, perPage: Int): List<Artist>
}