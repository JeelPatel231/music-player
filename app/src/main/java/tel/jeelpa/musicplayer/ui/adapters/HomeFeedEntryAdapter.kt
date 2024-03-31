package tel.jeelpa.musicplayer.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import androidx.paging.map
import androidx.recyclerview.widget.DiffUtil
import tel.jeelpa.musicplayer.common.clients.AlbumFeedEntry
import tel.jeelpa.musicplayer.common.clients.ArtistFeedEntry
import tel.jeelpa.musicplayer.common.clients.HomeFeedEntry
import tel.jeelpa.musicplayer.common.clients.TrackFeedEntry
import tel.jeelpa.musicplayer.databinding.ItemHomeFeedEntryBinding
import tel.jeelpa.musicplayer.models.ItemSmallDataClass
import tel.jeelpa.musicplayer.models.LazyAppTrack
import tel.jeelpa.musicplayer.models.toLazyAppTrack
import tel.jeelpa.musicplayer.models.toSmallMedia
import tel.jeelpa.musicplayer.ui.utils.setupHorizontalGridWithAdapter
import tel.jeelpa.musicplayer.ui.utils.setupHorizontalWithAdapter
import tel.jeelpa.musicplayer.utils.observeFlow

object HomeFeedComparator : DiffUtil.ItemCallback<HomeFeedEntry>() {
    override fun areItemsTheSame(oldItem: HomeFeedEntry, newItem: HomeFeedEntry): Boolean {
        return oldItem.key == newItem.key
    }

    override fun areContentsTheSame(oldItem: HomeFeedEntry, newItem: HomeFeedEntry): Boolean {
        return areItemsTheSame(oldItem, newItem)
    }
}

interface OnItemClick<T> {
    fun onClick(entry: T)
    fun onLongClick(entry: T): Boolean
}

data class ItemClickUseCase(
    val track: OnItemClick<LazyAppTrack>,
    val artist: OnItemClick<ItemSmallDataClass>,
    val album: OnItemClick<ItemSmallDataClass>,
)

class HomeFeedEntryAdapter(
    private val onClickUseCase: ItemClickUseCase,
) : LifecyclePagingAdapter<HomeFeedEntry, ItemHomeFeedEntryBinding>(HomeFeedComparator) {
    override fun inflateCallback(
        layoutInflator: LayoutInflater, viewGroup: ViewGroup?, attachToParent: Boolean
    ): ItemHomeFeedEntryBinding {
        return ItemHomeFeedEntryBinding.inflate(layoutInflator, viewGroup, attachToParent)
    }

    private fun handleTrackFeed(
        entry: TrackFeedEntry, binding: ItemHomeFeedEntryBinding, lifecycle: Lifecycle
    ) {
        val adapter = MediaItemAdapterLinear(
            onItemClick = onClickUseCase.track::onClick,
            onItemLongClick = onClickUseCase.track::onLongClick
        )
        binding.entryTitle.text = entry.key
        binding.entryRecycler.setupHorizontalGridWithAdapter(adapter)

        entry.data.observeFlow(lifecycle) { page ->
            adapter.submitData(page.map { it.toLazyAppTrack(lifecycle.coroutineScope) })
        }

    }

    private fun handleArtistFeed(
        entry: ArtistFeedEntry, binding: ItemHomeFeedEntryBinding, lifecycle: Lifecycle
    ) {
        val adapter = MediaItemAdapter(
            onItemClick = onClickUseCase.artist::onClick,
            onItemLongClick = onClickUseCase.artist::onLongClick
        )
        binding.entryTitle.text = entry.key
        binding.entryRecycler.setupHorizontalWithAdapter(adapter)

        entry.data.observeFlow(lifecycle) { page ->
            adapter.submitData(page.map { it.toSmallMedia() })
        }
    }


    private fun handleAlbumFeed(
        entry: AlbumFeedEntry, binding: ItemHomeFeedEntryBinding, lifecycle: Lifecycle
    ) {
        val adapter = MediaItemAdapter(
            onItemClick = onClickUseCase.album::onClick,
            onItemLongClick = onClickUseCase.album::onLongClick
        )
        binding.entryTitle.text = entry.key
        binding.entryRecycler.setupHorizontalWithAdapter(adapter)

        entry.data.observeFlow(lifecycle) { page ->
            adapter.submitData(page.map { it.toSmallMedia() })
        }
    }


    override fun onBind(
        binding: ItemHomeFeedEntryBinding,
        entry: HomeFeedEntry,
        position: Int,
        lifecycle: Lifecycle
    ) {
        when (entry) {
            is TrackFeedEntry -> handleTrackFeed(entry, binding, lifecycle)
            is AlbumFeedEntry -> handleAlbumFeed(entry, binding, lifecycle)
            is ArtistFeedEntry -> handleArtistFeed(entry, binding, lifecycle)
        }
    }
}
