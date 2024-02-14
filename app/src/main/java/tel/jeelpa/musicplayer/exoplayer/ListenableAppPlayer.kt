package tel.jeelpa.musicplayer.exoplayer

import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import tel.jeelpa.musicplayer.player.FlowPlayerListener
import tel.jeelpa.musicplayer.player.models.PlaybackState
import tel.jeelpa.musicplayer.player.models.RepeatMode

class ExoplayerListenerAdapter(player: ExoPlayer) :
    /* AppPlayer Implementation */ ExoplayerImpl(player),
    /* FlowListener Implementation */ FlowPlayerListener {

    private val _isPlaying = MutableStateFlow(player.isPlaying)
    private val _playbackState = MutableStateFlow(mapExoPlaybackStateToApp(player.playbackState))
    private val _currentMediaItem = null // TODO
    private val _repeatMode = MutableStateFlow(mapRepeatModeToApp(player.repeatMode))
    private val _shuffle = MutableStateFlow(player.shuffleModeEnabled)


    override val isPlaying = _isPlaying.asStateFlow()
    override val playbackState = _playbackState.asStateFlow()
    override val currentMediaItem get() = TODO()
    override val repeatMode = _repeatMode.asStateFlow()
    override val shuffle = _shuffle.asStateFlow()


    private fun mapExoPlaybackStateToApp(playbackState: Int) = when (playbackState) {
        ExoPlayer.STATE_IDLE -> PlaybackState.Idle
        ExoPlayer.STATE_ENDED -> PlaybackState.Stopped
        ExoPlayer.STATE_BUFFERING -> PlaybackState.Buffering
        ExoPlayer.STATE_READY -> PlaybackState.Ready
        else -> error("Unknown ExoPlayer State, Failed to map to app playback state")
    }

    private fun mapRepeatModeToApp(repeatMode: Int) = when (repeatMode) {
        ExoPlayer.REPEAT_MODE_ALL -> RepeatMode.All
        ExoPlayer.REPEAT_MODE_ONE -> RepeatMode.One
        ExoPlayer.REPEAT_MODE_OFF -> RepeatMode.Off
        else -> error("Unknown Repeat Mode")
    }

    private val exoplayerListener = object : Player.Listener {
        override fun onIsPlayingChanged(isPlaying: Boolean) {
            _isPlaying.value = isPlaying
        }

        override fun onPlaybackStateChanged(playbackState: Int) {
            _playbackState.value = mapExoPlaybackStateToApp(playbackState)
        }

        override fun onRepeatModeChanged(repeatMode: Int) {
            val newMode = mapRepeatModeToApp(repeatMode)
            _repeatMode.value = newMode
        }

        override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {
            _shuffle.value = shuffleModeEnabled
        }

        override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
            super.onMediaItemTransition(mediaItem, reason) // TODO
        }

    }

    init {
        player.addListener(exoplayerListener)
    }

}
