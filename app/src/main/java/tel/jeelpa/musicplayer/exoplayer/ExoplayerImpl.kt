package tel.jeelpa.musicplayer.exoplayer

import androidx.media3.common.C
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import tel.jeelpa.musicplayer.models.AppTrack
import tel.jeelpa.musicplayer.player.AppPlayer
import tel.jeelpa.musicplayer.player.models.Duration
import tel.jeelpa.musicplayer.player.models.PlaybackState
import tel.jeelpa.musicplayer.player.models.RepeatMode

open class ExoplayerImpl(
    private val exoplayer: ExoPlayer
) : AppPlayer {

    override fun play() =
        exoplayer.play()

    override fun pause() =
        exoplayer.pause()

    override fun stop() =
        exoplayer.stop()

    override fun isPlaying(): Boolean =
        exoplayer.isPlaying

    override fun getPlaybackState(): PlaybackState =
        when (exoplayer.playbackState) {
            ExoPlayer.STATE_IDLE -> PlaybackState.Idle
            ExoPlayer.STATE_ENDED -> PlaybackState.Stopped
            ExoPlayer.STATE_BUFFERING -> PlaybackState.Buffering
            ExoPlayer.STATE_READY -> PlaybackState.Ready
            else -> error("Unknown ExoPlayer State, Failed to map to app playback state")
        }

    override fun getRepeatMode(): RepeatMode =
        when (exoplayer.repeatMode) {
            Player.REPEAT_MODE_OFF -> RepeatMode.Off
            Player.REPEAT_MODE_ONE -> RepeatMode.One
            Player.REPEAT_MODE_ALL -> RepeatMode.All
            else -> error("Unknown Repeat Mode")
        }


    override fun getShuffle(): Boolean =
        exoplayer.shuffleModeEnabled

    override fun getDuration(): Duration =
        when (val duration = exoplayer.duration) {
            C.TIME_UNSET -> Duration.Unknown
            else -> Duration.Known(duration)
        }

    override fun getPosition(): Duration.Known =
        Duration.Known(exoplayer.currentPosition)

    override fun setMediaItem(appTrack: AppTrack) =
        exoplayer.setMediaItem(appTrack.toMediaItem())

    override fun addMediaItem(appTrack: AppTrack, index: Int?) =
        if(index == null) exoplayer.addMediaItem(appTrack.toMediaItem())
        else exoplayer.addMediaItem(index, appTrack.toMediaItem())

    override fun removeMediaItem(from: Int, to: Int) =
        exoplayer.removeMediaItems(from, to)

    override fun getCurrentMediaItem(): AppTrack? =
        exoplayer.currentMediaItem?.toTrack()

    override fun getCurrentMediaItemIndex(): Int =
        exoplayer.currentMediaItemIndex

    override fun getMediaItemCount(): Int =
        exoplayer.mediaItemCount

    override fun getTimeline(): List<AppTrack> =
        (0 until exoplayer.mediaItemCount)
//    (exoplayer.currentMediaItemIndex until exoplayer.mediaItemCount)
        .map { exoplayer.getMediaItemAt(it) }
        .map { it.toTrack() }

    override fun hasNextMediaItem(): Boolean =
        exoplayer.hasNextMediaItem()

    override fun hasPreviousMediaItem(): Boolean =
        exoplayer.hasPreviousMediaItem()

    override fun clearMediaItems() =
        exoplayer.clearMediaItems()

    override fun seekTo(positionMs: Long) =
        exoplayer.seekTo(positionMs)

    override fun seekToMediaItem(index: Int) {
        exoplayer.seekTo(index, 0)
    }

    override fun next() =
        exoplayer.seekToNextMediaItem()

    override fun previous() =
        exoplayer.seekToPreviousMediaItem()

    override fun setRepeatMode(mode: RepeatMode) {
        exoplayer.repeatMode = when (mode) {
            RepeatMode.Off -> Player.REPEAT_MODE_OFF
            RepeatMode.One -> Player.REPEAT_MODE_ONE
            RepeatMode.All -> Player.REPEAT_MODE_ALL
        }
    }

    override fun setShuffle(enable: Boolean) {
        exoplayer.shuffleModeEnabled = enable
    }
}