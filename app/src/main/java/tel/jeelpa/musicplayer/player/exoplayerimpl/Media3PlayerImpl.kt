package tel.jeelpa.musicplayer.player.exoplayerimpl

import androidx.media3.common.C
import androidx.media3.common.Player
import tel.jeelpa.musicplayer.models.EagerAppTrack
import tel.jeelpa.musicplayer.player.AppPlayer
import tel.jeelpa.musicplayer.player.models.Duration
import tel.jeelpa.musicplayer.player.models.PlaybackState
import tel.jeelpa.musicplayer.player.models.RepeatMode

open class Media3PlayerImpl(
    private val player: Player
) : AppPlayer {

    override fun play() =
        player.play()

    override fun pause() =
        player.pause()

    override fun stop() =
        player.stop()

    override fun isPlaying(): Boolean =
        player.isPlaying

    override fun getPlaybackState(): PlaybackState =
        when (player.playbackState) {
            Player.STATE_IDLE -> PlaybackState.Idle
            Player.STATE_ENDED -> PlaybackState.Stopped
            Player.STATE_BUFFERING -> PlaybackState.Buffering
            Player.STATE_READY -> PlaybackState.Ready
            else -> error("Unknown Player State, Failed to map to app playback state")
        }

    override fun getRepeatMode(): RepeatMode =
        when (player.repeatMode) {
            Player.REPEAT_MODE_OFF -> RepeatMode.Off
            Player.REPEAT_MODE_ONE -> RepeatMode.One
            Player.REPEAT_MODE_ALL -> RepeatMode.All
            else -> error("Unknown Repeat Mode")
        }


    override fun getShuffle(): Boolean =
        player.shuffleModeEnabled

    override fun getDuration(): Duration =
        when (val duration = player.duration) {
            C.TIME_UNSET -> Duration.Unknown
            else -> Duration.Known(duration)
        }

    override fun getPosition(): Duration.Known =
        Duration.Known(player.currentPosition)

    override fun setMediaItem(appTrack: EagerAppTrack) =
        player.setMediaItem(appTrack.toMediaItem())

    override fun addMediaItem(appTrack: EagerAppTrack, index: Int?) =
        if (index == null) player.addMediaItem(appTrack.toMediaItem())
        else player.addMediaItem(index, appTrack.toMediaItem())

    override fun removeMediaItem(from: Int, to: Int) =
        player.removeMediaItems(from, to)

    override fun getCurrentMediaItem(): EagerAppTrack? =
        player.currentMediaItem?.toTrack()

    override fun getCurrentMediaItemIndex(): Int =
        player.currentMediaItemIndex

    override fun getMediaItemCount(): Int =
        player.mediaItemCount

    override fun getTimeline(): List<EagerAppTrack> =
        (0 until player.mediaItemCount)
            .map { player.getMediaItemAt(it) }
            .map { it.toTrack() }

    override fun hasNextMediaItem(): Boolean =
        player.hasNextMediaItem()

    override fun hasPreviousMediaItem(): Boolean =
        player.hasPreviousMediaItem()

    override fun clearMediaItems() =
        player.clearMediaItems()

    override fun seekTo(positionMs: Long) =
        player.seekTo(positionMs)

    override fun seekToMediaItem(index: Int) {
        player.seekTo(index, 0)
    }

    override fun next() =
        player.seekToNextMediaItem()

    override fun previous() =
        player.seekToPreviousMediaItem()

    override fun setRepeatMode(mode: RepeatMode) {
        player.repeatMode = when (mode) {
            RepeatMode.Off -> Player.REPEAT_MODE_OFF
            RepeatMode.One -> Player.REPEAT_MODE_ONE
            RepeatMode.All -> Player.REPEAT_MODE_ALL
        }
    }

    override fun setShuffle(enable: Boolean) {
        player.shuffleModeEnabled = enable
    }
}