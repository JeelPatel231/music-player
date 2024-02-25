package tel.jeelpa.musicplayer.common.models

interface Artist {
    fun getUrl(): String
    suspend fun getName(): String
    suspend fun getAvatar(): String?
    suspend fun getSongs(offset: Int, limit: Int): List<Track>
    suspend fun getAlbums(offset: Int, limit: Int): List<Album>
    suspend fun getSingles(offset: Int, limit: Int): List<Album>
}
