package tel.jeelpa.musicplayer.models

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
interface BaseAppTrack {
    val id: String
    val name: String
    val thumbnail: String?
    val artist: String
}

interface LazyAppTrack: BaseAppTrack {
    suspend fun mediaSource(): AppMediaSource
}

interface EagerAppTrack: BaseAppTrack {
    val mediaSource: AppMediaSource
}

/**
 * data is loaded eagerly from suspending functions
 *
 * and the lazy loading data is just a function
 * which gets called when needed.
 * */
suspend fun Track.toLazyAppTrack(coroutineScope: CoroutineScope): LazyAppTrack  {
    val name = withContext(Dispatchers.IO) {
        getName()
    }
    val thumbnail = withContext(Dispatchers.IO) {
        getCover()
    }

    val artist = withContext(Dispatchers.IO) {
        getArtists().first().getName()
    }

    val song = coroutineScope.async(Dispatchers.IO, CoroutineStart.LAZY) {
        getMediaSource().first().toAppMediaSource()
    }

    return object : LazyAppTrack {
        override val id = getUrl()
        override val name: String = name
        override val thumbnail: String = thumbnail
        override val artist = artist

        override suspend fun mediaSource(): AppMediaSource =
            song.await()
    }
}

suspend fun LazyAppTrack.loadEager(): EagerAppTrack {
    val loadedMediaSource = this@loadEager.mediaSource()

    return object : EagerAppTrack {
        override val mediaSource: AppMediaSource = loadedMediaSource
        override val id: String = this@loadEager.id
        override val name: String = this@loadEager.name
        override val thumbnail: String? = this@loadEager.thumbnail
        override val artist: String = this@loadEager.artist
    }
}