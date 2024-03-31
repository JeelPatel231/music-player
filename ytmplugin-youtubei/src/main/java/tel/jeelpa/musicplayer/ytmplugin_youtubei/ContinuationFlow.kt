package tel.jeelpa.musicplayer.ytmplugin_youtubei

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException


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
