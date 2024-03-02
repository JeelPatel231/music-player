package tel.jeelpa.musicplayer.player

import kotlinx.coroutines.flow.Flow
import tel.jeelpa.musicplayer.models.EagerAppTrack
import tel.jeelpa.musicplayer.player.models.PlaybackState
import tel.jeelpa.musicplayer.player.models.RepeatMode

//interface PlayerListener {
//    fun onIsPlayingChanged(isPlaying: Boolean) {}
//
//    fun onPlaybackStateChanged(playbackState: PlaybackState) {}
//
//    fun mediaItemChanged(mediaItem: AppMediaSource) {}
//
//    fun onRepeatModeChanged(repeatMode: RepeatMode) {}
//
//    fun onShuffleModeChanged(enabled: Boolean) {}
//}


interface FlowPlayerListener {
    fun listenToCurrentPosition(delay: Long = 1000): Flow<Long>

    val isPlaying: Flow<Boolean>

    val playbackState: Flow<PlaybackState>

    val currentMediaItem: Flow<EagerAppTrack?>

    val repeatMode: Flow<RepeatMode>

    val shuffle: Flow<Boolean>

    val timeline: Flow<List<EagerAppTrack>> // TODO: is it eager app track or it doesnt matter
}
