package tel.jeelpa.musicplayer.player

import tel.jeelpa.musicplayer.player.models.PlaybackState
import tel.jeelpa.musicplayer.player.models.RepeatMode
import tel.jeelpa.musicplayer.player.models.Song

interface PlayerListener {
    fun onPlaybackStateChanged(playbackState: PlaybackState) {}

    fun mediaItemChanged(mediaItem: Song) {}

    fun onRepeatModeChanged(repeatMode: RepeatMode) {}

    fun onShuffleModeChanged(enabled: Boolean) {}
}
