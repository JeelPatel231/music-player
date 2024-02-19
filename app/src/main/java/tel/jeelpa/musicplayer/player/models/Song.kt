package tel.jeelpa.musicplayer.player.models

import tel.jeelpa.musicplayer.common.clients.AbstractMediaSource
import tel.jeelpa.musicplayer.common.clients.StringMediaSource


sealed interface Song {
    class HttpUrl(val url: String): Song
}


fun AbstractMediaSource.toSong(): Song = when(this) {
    is StringMediaSource -> Song.HttpUrl(url)
}
