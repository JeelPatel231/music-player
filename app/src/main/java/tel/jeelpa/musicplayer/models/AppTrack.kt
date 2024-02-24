package tel.jeelpa.musicplayer.models

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import tel.jeelpa.musicplayer.common.clients.TrackClient
import tel.jeelpa.musicplayer.player.models.AppMediaSource
import tel.jeelpa.musicplayer.player.models.toAppMediaSource

/**
 * Used in UI to display data
 * */
interface AppTrack {
    val id: String
    val name: String
    val thumbnail: String
    val artist: String
    val mediaSource: AppMediaSource
}

/**
 * data is loaded eagerly from suspending functions
 *
 * and the lazy loading data is just a function
 * which gets called when needed.
 * */
suspend fun TrackClient.toTrack(): AppTrack  {
    val name = withContext(Dispatchers.IO) {
        getName()
    }
    val thumbnail = withContext(Dispatchers.IO) {
        getCover()
    }

    val artist = withContext(Dispatchers.IO) {
        getArtists().first().getName()
    }

    val song = withContext(Dispatchers.IO) {
        getMediaSource().first().toAppMediaSource()
    }

    return object : AppTrack {
        override val id = getUrl()
        override val name: String = name
        override val thumbnail: String = thumbnail
        override val artist = artist
        override val mediaSource = song
    }
}

