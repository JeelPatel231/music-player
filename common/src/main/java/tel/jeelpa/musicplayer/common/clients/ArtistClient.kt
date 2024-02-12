package tel.jeelpa.musicplayer.common.clients

interface ArtistClient {
    fun getAvatar(): String
    fun getSongs(offset: Int, limit: Int): List<TrackClient>
    fun getAlbums(offset: Int, limit: Int): List<AlbumClient>
    fun getSingles(offset: Int, limit: Int): List<AlbumClient>
}
