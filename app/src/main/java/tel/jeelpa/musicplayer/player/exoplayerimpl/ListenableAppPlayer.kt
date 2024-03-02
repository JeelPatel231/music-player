package tel.jeelpa.musicplayer.player.exoplayerimpl

import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import tel.jeelpa.musicplayer.models.EagerAppTrack
import tel.jeelpa.musicplayer.player.FlowPlayerListener
import tel.jeelpa.musicplayer.player.models.PlaybackState
import tel.jeelpa.musicplayer.player.models.RepeatMode

class Media3PlayerListenerAdapter(
    private val player: Player
) :
/* AppPlayer Implementation */ Media3PlayerImpl(player),
/* FlowListener Implementation */ FlowPlayerListener {

    private val _isPlaying = MutableStateFlow(player.isPlaying)
    private val _playbackState = MutableStateFlow(mapExoPlaybackStateToApp(player.playbackState))
    private val _currentMediaItem = MutableStateFlow<EagerAppTrack?>(null)
    private val _repeatMode = MutableStateFlow(mapRepeatModeToApp(player.repeatMode))
    private val _shuffle = MutableStateFlow(player.shuffleModeEnabled)
    private val _timeline = MutableStateFlow(emptyList<EagerAppTrack>())

    override fun listenToCurrentPosition(delay: Long): Flow<Long> = flow {
        while(true) {
            emit(player.currentPosition)
            delay(1000)
        }
    }


    override val isPlaying = _isPlaying.asStateFlow()
    override val playbackState = _playbackState.asStateFlow()
    override val currentMediaItem = _currentMediaItem.asStateFlow()
    override val repeatMode = _repeatMode.asStateFlow()
    override val shuffle = _shuffle.asStateFlow()
    override val timeline = _timeline.asStateFlow()


    private fun mapExoPlaybackStateToApp(playbackState: Int) = when (playbackState) {
        Player.STATE_IDLE -> PlaybackState.Idle
        Player.STATE_ENDED -> PlaybackState.Stopped
        Player.STATE_BUFFERING -> PlaybackState.Buffering
        Player.STATE_READY -> PlaybackState.Ready
        else -> error("Unknown Player State, Failed to map to app playback state")
    }


    private fun mapRepeatModeToApp(repeatMode: Int) = when (repeatMode) {
        Player.REPEAT_MODE_ALL -> RepeatMode.All
        Player.REPEAT_MODE_ONE -> RepeatMode.One
        Player.REPEAT_MODE_OFF -> RepeatMode.Off
        else -> error("Unknown Repeat Mode")
    }

    private val playerListener = object : Player.Listener {
        override fun onEvents(player: Player, events: Player.Events) {
            if(events.contains(Player.EVENT_TIMELINE_CHANGED)) {
                _timeline.value = getTimeline()
            }
        }

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
            _currentMediaItem.value = mediaItem?.toTrack()
        }

        override fun onPositionDiscontinuity(
            oldPosition: Player.PositionInfo,
            newPosition: Player.PositionInfo,
            reason: Int
        ) {
            // TODO()
        }

    }

    init {
        player.addListener(playerListener)
    }

}
