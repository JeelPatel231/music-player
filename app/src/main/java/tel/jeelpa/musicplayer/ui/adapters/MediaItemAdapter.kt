package tel.jeelpa.musicplayer.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import coil.load
import tel.jeelpa.musicplayer.databinding.ItemMediaSmallBinding
import tel.jeelpa.musicplayer.models.LazyAppTrack

object AppTrackComparator: DiffUtil.ItemCallback<LazyAppTrack>() {
    override fun areItemsTheSame(oldItem: LazyAppTrack, newItem: LazyAppTrack): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: LazyAppTrack, newItem: LazyAppTrack): Boolean {
        return oldItem.id == newItem.id
    }

}

class MediaItemAdapter(
    private val onItemClick: (LazyAppTrack) -> Unit = {},
    private val onItemLongClick: (LazyAppTrack) -> Boolean = { false },
): GenericListAdapter<LazyAppTrack, ItemMediaSmallBinding>(AppTrackComparator){
    override fun inflateCallback(
        layoutInflator: LayoutInflater,
        viewGroup: ViewGroup?,
        attachToParent: Boolean
    ): ItemMediaSmallBinding {
        return ItemMediaSmallBinding.inflate(layoutInflator, viewGroup, attachToParent)
    }

    override fun onBind(binding: ItemMediaSmallBinding, entry: LazyAppTrack, position: Int) {
        binding.root.setOnClickListener { onItemClick(entry) }
        binding.root.setOnLongClickListener {
            onItemLongClick(entry)
        }
        binding.mediaArt.load(entry.thumbnail)
        binding.mediaTitle.text = entry.name
    }
}