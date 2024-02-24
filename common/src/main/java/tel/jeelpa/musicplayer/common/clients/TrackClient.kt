package tel.jeelpa.musicplayer.common.clients

interface TrackClient {
    fun getUrl(): String
    suspend fun getName(): String
    suspend fun getMediaSource(): List<AbstractMediaSource>
    suspend fun getRadio(): List<TrackClient>
    suspend fun getCover(): String

    suspend fun getArtists():  List<ArtistClient>
    suspend fun getAlbum(): AlbumClient
}
