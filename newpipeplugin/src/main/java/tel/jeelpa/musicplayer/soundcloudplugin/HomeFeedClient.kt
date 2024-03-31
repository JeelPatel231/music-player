package tel.jeelpa.musicplayer.soundcloudplugin

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.withContext
import okio.IOException
import org.schabi.newpipe.extractor.Page
import org.schabi.newpipe.extractor.StreamingService
import tel.jeelpa.musicplayer.common.clients.HomeFeedClient
import tel.jeelpa.musicplayer.common.clients.HomeFeedEntry
import tel.jeelpa.musicplayer.common.clients.TrackFeedEntry
import tel.jeelpa.musicplayer.common.models.Track

fun StreamingService.getHomeFeedClient(): NPHomeFeedClient {
    return NPHomeFeedClient(this)
}

class ContinuationSource<T : Any, C : Any>(
    private val loadNext: suspend (next: C?) -> LoadResult.Page<C, T>
) : PagingSource<C, T>() {

    override fun getRefreshKey(state: PagingState<C, T>): C? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.nextKey
        }
    }

    override suspend fun load(params: LoadParams<C>): LoadResult<C, T> {
        return try {
            withContext(Dispatchers.IO) { loadNext(params.key) }
        } catch (e: IOException) {
            LoadResult.Error(e)
        }
    }
}

fun <C: Any, T: Any> continuationFlow(
    load: suspend (next: C?) -> PagingSource.LoadResult.Page<C, T>
) = Pager(
    config = PagingConfig(pageSize = 1, enablePlaceholders = false),
    pagingSourceFactory = { ContinuationSource(load) }
).flow

class NPHomeFeedClient(
    private val service: StreamingService,
) : HomeFeedClient {
    private val kioskExtractor = service.kioskList.defaultKioskExtractor
    override fun getHomeFeed(): Flow<PagingData<HomeFeedEntry>> {
        return flowOf(
            PagingData.from(
                listOf(
                    TrackFeedEntry(
                        "Trending",
                        continuationFlow<Page, Track> {
                            val npFeed = if(it != null){
                                kioskExtractor.getPage(it)
                            } else {
                                kioskExtractor.fetchPage()
                                kioskExtractor.initialPage
                            }

                            val nextPage = if(npFeed.hasNextPage()) npFeed.nextPage else null
                            println("NEXT PAGE: $nextPage")
                            PagingSource.LoadResult.Page(
                                npFeed.items.map { e -> service.getTrack(e.url, e.name, e.thumbnailUrl) },
                                it,
                                nextPage,
                            )
                        }
                    ),
                )
            )
        )
    }

}
