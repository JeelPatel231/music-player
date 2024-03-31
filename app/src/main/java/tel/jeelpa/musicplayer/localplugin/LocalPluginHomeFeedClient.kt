package tel.jeelpa.musicplayer.localplugin

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import tel.jeelpa.musicplayer.common.clients.AlbumFeedEntry
import tel.jeelpa.musicplayer.common.clients.ArtistFeedEntry
import tel.jeelpa.musicplayer.common.clients.HomeFeedClient
import tel.jeelpa.musicplayer.common.clients.HomeFeedEntry
import tel.jeelpa.musicplayer.common.clients.TrackFeedEntry
import tel.jeelpa.musicplayer.localplugin.content.LocalPluginContentResolver
import tel.jeelpa.musicplayer.ui.utils.toPagedData

class LocalPluginHomeFeedClient(
    private val resolver: LocalPluginContentResolver
): HomeFeedClient {
    override fun getHomeFeed(): Flow<PagingData<HomeFeedEntry>> {
        return flowOf(PagingData.from(listOf(
            TrackFeedEntry(
                "Songs",
                flowOf(resolver.trackResolver.getAll().toPagedData()),
            ),
            ArtistFeedEntry(
                "Artists",
                flowOf(resolver.artistResolver.getAll().toPagedData()),
            ),
            AlbumFeedEntry(
                "Albums",
                flowOf(resolver.albumResolver.getAll().toPagedData()),
            )
        )))
    }
}