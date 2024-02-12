package tel.jeelpa.musicplayer.common.clients

sealed class AbstractMediaSource;


data class StringMediaSource(val url: String): AbstractMediaSource()
