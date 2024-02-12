package tel.jeelpa.musicplayer.common.clients


interface HomeFeedClient {
    fun getSongs(offset: Int, limit: Int): List<TrackClient>
    fun getAlbums(offset: Int, limit: Int) : List<AlbumClient>
    fun getArtists(count: Int, limit: Int) : List<ArtistClient>
}
