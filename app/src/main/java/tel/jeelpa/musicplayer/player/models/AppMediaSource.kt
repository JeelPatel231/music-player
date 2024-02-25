package tel.jeelpa.musicplayer.player.models

import tel.jeelpa.musicplayer.common.models.AbstractMediaSource
import tel.jeelpa.musicplayer.common.models.StringMediaSource


sealed interface AppMediaSource {
    data class HttpUrl(val url: String): AppMediaSource
}


fun AbstractMediaSource.toAppMediaSource(): AppMediaSource = when(this) {
    is StringMediaSource -> AppMediaSource.HttpUrl(url)
}
