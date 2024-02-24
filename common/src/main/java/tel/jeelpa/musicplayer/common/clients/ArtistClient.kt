package tel.jeelpa.musicplayer.common.clients

interface ArtistClient {
    fun getUrl(): String
    suspend fun getName(): String
    suspend fun getAvatar(): String?
    suspend fun getSongs(offset: Int, limit: Int): List<TrackClient>
    suspend fun getAlbums(offset: Int, limit: Int): List<AlbumClient>
    suspend fun getSingles(offset: Int, limit: Int): List<AlbumClient>
}
