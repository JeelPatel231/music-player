package tel.jeelpa.musicplayer.player

import tel.jeelpa.musicplayer.models.EagerAppTrack
import tel.jeelpa.musicplayer.player.models.Duration
import tel.jeelpa.musicplayer.player.models.PlaybackState
import tel.jeelpa.musicplayer.player.models.RepeatMode


interface AppPlayer {
    fun play()

    fun pause()

    fun stop()

    fun next()

    fun previous()


    // getters
    fun getDuration(): Duration

    fun getPosition(): Duration.Known


    fun getProgress() = when (val dur = getDuration()) {
        is Duration.Unknown -> 0F
        is Duration.Known -> (getPosition().length / dur.length.toFloat()) * 100
    }

    fun isPlaying(): Boolean

    fun getPlaybackState(): PlaybackState

    fun getRepeatMode() : RepeatMode

    fun getShuffle() : Boolean

    fun getCurrentMediaItem(): EagerAppTrack?

    fun getCurrentMediaItemIndex(): Int

    fun getMediaItemCount(): Int

    fun getTimeline(): List<EagerAppTrack>

    // convenience has next and has previous methods
    fun hasNextMediaItem(): Boolean

    fun hasPreviousMediaItem(): Boolean

    /// setters
    fun setMediaItem(appTrack: EagerAppTrack)

    fun addMediaItem(appTrack: EagerAppTrack, index: Int? = null)

    fun removeMediaItem(from: Int, to: Int)

    fun clearMediaItems()

    fun setRepeatMode(mode: RepeatMode)

    fun setShuffle(enable: Boolean)

    /// Playback Seek
    fun seekTo(positionMs: Long)

    fun seekToMediaItem(index: Int)
}