package tel.jeelpa.musicplayer.common.clients

interface TrackClient {
    fun getName(): String
    fun getMediaSource(): List<AbstractMediaSource>
    fun getRadio(): List<TrackClient>
    fun getCover(): String

    fun getArtists():  List<ArtistClient>
    fun getAlbum(): AlbumClient
}
