package tel.jeelpa.musicplayer.player

import tel.jeelpa.musicplayer.player.models.PlaybackState
import tel.jeelpa.musicplayer.player.models.RepeatMode
import tel.jeelpa.musicplayer.player.models.Song

class ListenableAppPlayer(
    private val player: AppPlayer,
    private val listener: PlayerListener,
): AppPlayer {
    private fun notify(cb: PlayerListener.() -> Unit) =
        listener.cb()

    override fun play() {
        player.play()
        notify { onPlaybackStateChanged(PlaybackState.Playing) }
    }

    override fun stop() {
        player.stop()
        notify { onPlaybackStateChanged(PlaybackState.Stopped) }
    }

    override fun pause() {
        player.pause()
        notify { onPlaybackStateChanged(PlaybackState.Paused) }
    }

    override fun getDuration() =
        player.getDuration()

    override fun isPlaying(): Boolean =
        player.isPlaying()

    override fun setMediaItem(song: Song) =
        player.setMediaItem(song)

    override fun addMediaItem(song: Song, index: Int) =
        player.addMediaItem(song, index)

    override fun removeMediaItem(from: Int, to: Int) =
        player.removeMediaItem(from, to)

    override fun getCurrentMediaItem(): Song =
        player.getCurrentMediaItem()

    override fun getMediaItemCount(): Int =
        player.getMediaItemCount()

    override fun clearMediaItems() =
        player.clearMediaItems()

    override fun seekTo(positionMs: Long) =
        player.seekTo(positionMs)

    override fun next() {
        player.next()
        notify { mediaItemChanged(player.getCurrentMediaItem()) }
    }

    override fun previous() {
        player.previous()
        notify { mediaItemChanged(player.getCurrentMediaItem()) }
    }

    override fun setRepeatMode(mode: RepeatMode) {
        player.setRepeatMode(mode)
        notify { onRepeatModeChanged(mode) }
    }

    override fun setShuffle(enable: Boolean) {
        player.setShuffle(enable)
        notify { onShuffleModeChanged(enable) }
    }
}