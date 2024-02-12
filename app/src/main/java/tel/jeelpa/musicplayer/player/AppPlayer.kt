package tel.jeelpa.musicplayer.player

import tel.jeelpa.musicplayer.player.models.Duration
import tel.jeelpa.musicplayer.player.models.RepeatMode
import tel.jeelpa.musicplayer.player.models.Song


interface AppPlayer {
    fun play()

    fun pause()

    fun stop()

    fun getDuration(): Duration

    fun isPlaying(): Boolean

    /// Media Items
    fun setMediaItem(song: Song)

    fun addMediaItem(song: Song, index: Int = 0)

    fun removeMediaItem(from: Int, to: Int)

    fun getCurrentMediaItem(): Song

    fun getMediaItemCount(): Int

    fun clearMediaItems()

    /// Playback Seek

    fun seekTo(positionMs: Long)

    /// Playlist
    fun next()

    fun previous()

    /// Playlist behaviour

    fun setRepeatMode(mode: RepeatMode)

    fun setShuffle(enable: Boolean)
}