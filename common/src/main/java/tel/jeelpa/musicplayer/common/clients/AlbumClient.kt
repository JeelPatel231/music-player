package tel.jeelpa.musicplayer.common.clients

interface AlbumClient {
    fun getUrl(): String
    suspend fun getName(): String
    suspend fun getAlbumArtists(): List<ArtistClient>
    suspend fun getAlbumArt(): String
    suspend fun getSongs(offset: Int, limit: Int): List<TrackClient>
}
