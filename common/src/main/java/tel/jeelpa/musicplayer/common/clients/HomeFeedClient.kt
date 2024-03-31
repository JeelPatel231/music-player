package tel.jeelpa.musicplayer.common.clients

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import tel.jeelpa.musicplayer.common.models.Album
import tel.jeelpa.musicplayer.common.models.Artist
import tel.jeelpa.musicplayer.common.models.Track


sealed interface HomeFeedEntry {
    val key: String
}

data class TrackFeedEntry(override val key: String, val data: Flow<PagingData<Track>>): HomeFeedEntry
data class ArtistFeedEntry(override val key: String, val data: Flow<PagingData<Artist>>): HomeFeedEntry
data class AlbumFeedEntry(override val key: String, val data: Flow<PagingData<Album>>): HomeFeedEntry


interface HomeFeedClient {
    fun getHomeFeed(): Flow<PagingData<HomeFeedEntry>>
}