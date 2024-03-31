package tel.jeelpa.musicplayer.models

import android.net.Uri
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import tel.jeelpa.musicplayer.common.models.Track
import tel.jeelpa.musicplayer.player.models.AppMediaSource
import tel.jeelpa.musicplayer.player.models.toAppMediaSource

/**
 * Used in UI to display data
 * */

interface LazyAppTrack : ItemSmallData {
    suspend fun mediaSource(): AppMediaSource

    suspend fun getArtist(): String
}

/**
 * data is loaded eagerly from suspending functions
 *
 * and the lazy loading data is just a function
 * which gets called when needed.
 * */
suspend fun Track.toLazyAppTrack(coroutineScope: CoroutineScope): LazyAppTrack {
    val name = withContext(Dispatchers.IO) {
        getName()
    }
    val thumbnail = withContext(Dispatchers.IO) {
        getCover()
    }

    val artist = coroutineScope.async(Dispatchers.IO, CoroutineStart.LAZY) {
        getArtists().first().getName()
    }

    val song = coroutineScope.async(Dispatchers.IO, CoroutineStart.LAZY) {
        getMediaSource().first().toAppMediaSource()
    }

    return object : LazyAppTrack {
        override val id = getUrl()
        override val title: String = name
        override val avatar: String = thumbnail

        override suspend fun mediaSource(): AppMediaSource =
            song.await()

        override suspend fun getArtist(): String =
            artist.await()
    }
}

suspend fun LazyAppTrack.toMediaItem(): MediaItem {
    val uri = when (val _media = mediaSource()) {
        is AppMediaSource.HttpUrl -> _media.url
    }

    val artist = getArtist()

    val metaData = MediaMetadata.Builder()
        .setArtworkUri(Uri.parse(avatar))
        .setTitle(title)
        .setArtist(artist)
        .build()

    return MediaItem.Builder()
        .setMediaId(id)
        .setUri(uri)
        .setMediaMetadata(metaData)
        .build()
}