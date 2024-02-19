package tel.jeelpa.musicplayer.common.clients

interface AlbumClient {
    fun getUrl(): String
    fun getName(): String
    fun getAlbumArtists(): List<ArtistClient>
    fun getAlbumArt(): String
    fun getSongs(offset: Int, limit: Int): List<TrackClient>
}
