package tel.jeelpa.musicplayer.player.models

sealed class Duration {
    data object Unknown: Duration()
    data class Known(val length: Long): Duration()
}
