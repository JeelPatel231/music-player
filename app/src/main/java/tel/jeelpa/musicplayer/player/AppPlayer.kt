package tel.jeelpa.musicplayer.player

import tel.jeelpa.musicplayer.player.models.Duration
import tel.jeelpa.musicplayer.player.models.PlaybackState
import tel.jeelpa.musicplayer.player.models.RepeatMode
import tel.jeelpa.musicplayer.player.models.Song


interface AppPlayer {
    fun play()

    fun pause()

    fun stop()

    fun next()

    fun previous()


    // getters
    fun getDuration(): Duration

    fun isPlaying(): Boolean

    fun getPlaybackState(): PlaybackState

    fun getRepeatMode() : RepeatMode

    fun getShuffle() : Boolean

    fun getCurrentMediaItem(): Song

    fun getMediaItemCount(): Int

    /// setters
    fun setMediaItem(song: Song)

    fun addMediaItem(song: Song, index: Int = 0)

    fun removeMediaItem(from: Int, to: Int)

    fun clearMediaItems()

    fun setRepeatMode(mode: RepeatMode)

    fun setShuffle(enable: Boolean)

    /// Playback Seek
    fun seekTo(positionMs: Long)
}