package tel.jeelpa.musicplayer.player.exoplayerimpl

import android.net.Uri
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import tel.jeelpa.musicplayer.models.EagerAppTrack
import tel.jeelpa.musicplayer.player.models.AppMediaSource


fun EagerAppTrack.toMediaItem(): MediaItem {
    val uri = when(val _media = mediaSource) {
        is AppMediaSource.HttpUrl -> _media.url
    }
    val metaData = MediaMetadata.Builder()
        .setArtworkUri(Uri.parse(thumbnail))
        .setTitle(name)
        .setArtist(artist)
        .build()

    return MediaItem.Builder()
        .setMediaId(id)
        .setUri(uri)
        .setMediaMetadata(metaData)
        .build()
}

fun MediaItem.toTrack(): EagerAppTrack {
    val currMeta = mediaMetadata
    return object : EagerAppTrack {
        override val id = mediaId
        override val name = (currMeta.displayTitle ?: currMeta.title!!).toString()
        override val thumbnail = currMeta.artworkUri.toString()
        override val artist = currMeta.artist!!.toString()
        override val mediaSource = AppMediaSource.HttpUrl(localConfiguration!!.uri.toString())
    }
}