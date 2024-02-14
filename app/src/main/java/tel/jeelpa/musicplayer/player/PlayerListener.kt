package tel.jeelpa.musicplayer.player

import kotlinx.coroutines.flow.Flow
import tel.jeelpa.musicplayer.player.models.PlaybackState
import tel.jeelpa.musicplayer.player.models.RepeatMode
import tel.jeelpa.musicplayer.player.models.Song

interface PlayerListener {
    fun onIsPlayingChanged(isPlaying: Boolean) {}

    fun onPlaybackStateChanged(playbackState: PlaybackState) {}

    fun mediaItemChanged(mediaItem: Song) {}

    fun onRepeatModeChanged(repeatMode: RepeatMode) {}

    fun onShuffleModeChanged(enabled: Boolean) {}
}


interface FlowPlayerListener : PlayerListener {
    val isPlaying: Flow<Boolean>

    val playbackState: Flow<PlaybackState>

    val currentMediaItem: Flow<Song>

    val repeatMode: Flow<RepeatMode>

    val shuffle: Flow<Boolean>
}
