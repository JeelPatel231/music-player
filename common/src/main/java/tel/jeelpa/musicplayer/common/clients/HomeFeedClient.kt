package tel.jeelpa.musicplayer.common.clients


interface HomeFeedClient {
    suspend fun getSongs(offset: Int, limit: Int): List<TrackClient>
    suspend fun getAlbums(offset: Int, limit: Int) : List<AlbumClient>
    suspend fun getArtists(count: Int, limit: Int) : List<ArtistClient>
}
