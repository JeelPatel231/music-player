package tel.jeelpa.musicplayer.player.models


sealed interface Song {
    class HttpUrl(val url: String): Song
}
