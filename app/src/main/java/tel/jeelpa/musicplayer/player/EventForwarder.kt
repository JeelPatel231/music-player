package tel.jeelpa.musicplayer.player

import tel.jeelpa.musicplayer.player.models.PlaybackState
import tel.jeelpa.musicplayer.player.models.RepeatMode
import tel.jeelpa.musicplayer.player.models.Song

class EventForwarder: PlayerListener {
    private val listeners: MutableList<PlayerListener> = mutableListOf()

    fun addListener(listener: PlayerListener) {
        listeners.add(listener)
    }

    private fun notify(callback: PlayerListener.() -> Unit){
        listeners.onEach { it.callback() }
    }

    override fun onPlaybackStateChanged(playbackState: PlaybackState) {
        notify { onPlaybackStateChanged(playbackState) }
    }

    override fun mediaItemChanged(mediaItem: Song) {
        notify { mediaItemChanged(mediaItem) }
    }

    override fun onRepeatModeChanged(repeatMode: RepeatMode) {
        notify { onRepeatModeChanged(repeatMode) }
    }

    override fun onShuffleModeChanged(enabled: Boolean) {
        notify { onShuffleModeChanged(enabled) }
    }
}