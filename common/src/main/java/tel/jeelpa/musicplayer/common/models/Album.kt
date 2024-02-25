package tel.jeelpa.musicplayer.common.models

interface Album {
    fun getUrl(): String
    suspend fun getName(): String
    suspend fun getAlbumArtists(): List<Artist>
    suspend fun getAlbumArt(): String
    suspend fun getSongs(offset: Int, limit: Int): List<Track>
}
