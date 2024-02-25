package tel.jeelpa.musicplayer.common.clients

import tel.jeelpa.musicplayer.common.models.Album
import tel.jeelpa.musicplayer.common.models.Artist
import tel.jeelpa.musicplayer.common.models.Track


interface HomeFeedClient {
    suspend fun getSongs(offset: Int, limit: Int): List<Track>
    suspend fun getAlbums(offset: Int, limit: Int) : List<Album>
    suspend fun getArtists(count: Int, limit: Int) : List<Artist>
}
