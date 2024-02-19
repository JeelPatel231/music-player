package tel.jeelpa.musicplayer.models

import tel.jeelpa.musicplayer.common.clients.TrackClient

data class Track(
    val name: String,
    val thumbnail: String,
    val url: String,
)

// adapter for plugins
fun TrackClient.toTrack(): Track =
    Track(
        getName(),
        getCover(),
        getUrl(),
    )
