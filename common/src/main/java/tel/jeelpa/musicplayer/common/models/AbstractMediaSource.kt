package tel.jeelpa.musicplayer.common.models

sealed class AbstractMediaSource;


data class StringMediaSource(val url: String): AbstractMediaSource()
