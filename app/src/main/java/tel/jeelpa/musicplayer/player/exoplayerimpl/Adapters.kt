package tel.jeelpa.musicplayer.player.exoplayerimpl

import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Player


fun Player.getProgress() = when(duration) {
    C.TIME_UNSET -> 0F
    else -> currentPosition/duration.toFloat() * 100
}

fun Player.getTimeline() = (0 until mediaItemCount).map { getMediaItemAt(it) }

fun Player.togglePlayPause() = if(isPlaying) pause() else play()

////


val MediaItem.title
    get() = mediaMetadata.title ?: mediaMetadata.displayTitle ?: "Unknown Title"

val MediaItem.artist
    get() = mediaMetadata.artist ?: "Unknown Artist"

val MediaItem.id
    get() = mediaId

val MediaItem.thumbnail
    get() = mediaMetadata.artworkUri