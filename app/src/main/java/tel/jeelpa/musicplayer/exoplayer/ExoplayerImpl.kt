package tel.jeelpa.musicplayer.exoplayer

import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import tel.jeelpa.musicplayer.player.AppPlayer
import tel.jeelpa.musicplayer.player.models.Duration
import tel.jeelpa.musicplayer.player.models.PlaybackState
import tel.jeelpa.musicplayer.player.models.RepeatMode
import tel.jeelpa.musicplayer.player.models.Song

open class ExoplayerImpl(
    private val exoplayer: ExoPlayer
) : AppPlayer {
    private fun Song.toMediaItem(): MediaItem = when (this) {
        is Song.HttpUrl -> MediaItem.fromUri(url)
    }

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

    override fun setMediaItem(song: Song) =
        exoplayer.setMediaItem(song.toMediaItem())

    override fun addMediaItem(song: Song, index: Int) =
        exoplayer.addMediaItem(index, song.toMediaItem())

    override fun removeMediaItem(from: Int, to: Int) =
        exoplayer.removeMediaItems(from, to)

    override fun getCurrentMediaItem(): Song {
        TODO("Not yet implemented")
    }

    override fun getMediaItemCount(): Int =
        exoplayer.mediaItemCount

    override fun clearMediaItems() =
        exoplayer.clearMediaItems()

    override fun seekTo(positionMs: Long) =
        exoplayer.seekTo(positionMs)

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