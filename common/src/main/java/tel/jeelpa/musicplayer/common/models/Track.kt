package tel.jeelpa.musicplayer.common.models

interface Track {
    fun getUrl(): String
    suspend fun getName(): String
    suspend fun getMediaSource(): List<AbstractMediaSource>
    suspend fun getRadio(): List<Track>
    suspend fun getCover(): String

    suspend fun getArtists():  List<Artist>
    suspend fun getAlbum(): Album
}
