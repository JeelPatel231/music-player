package tel.jeelpa.musicplayer.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import coil.load
import tel.jeelpa.musicplayer.databinding.ItemMediaSmallBinding
import tel.jeelpa.musicplayer.models.AppTrack

object AppTrackComparator: DiffUtil.ItemCallback<AppTrack>() {
    override fun areItemsTheSame(oldItem: AppTrack, newItem: AppTrack): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: AppTrack, newItem: AppTrack): Boolean {
        return oldItem.id == newItem.id
    }

}

class MediaItemAdapter(
    private val onItemClick: (AppTrack) -> Unit = {},
    private val onItemLongClick: (AppTrack) -> Boolean = { false },
): GenericListAdapter<AppTrack, ItemMediaSmallBinding>(AppTrackComparator){
    override fun inflateCallback(
        layoutInflator: LayoutInflater,
        viewGroup: ViewGroup?,
        attachToParent: Boolean
    ): ItemMediaSmallBinding {
        return ItemMediaSmallBinding.inflate(layoutInflator, viewGroup, attachToParent)
    }

    override fun onBind(binding: ItemMediaSmallBinding, entry: AppTrack, position: Int) {
        binding.root.setOnClickListener { onItemClick(entry) }
        binding.root.setOnLongClickListener {
            onItemLongClick(entry)
        }
        binding.mediaArt.load(entry.thumbnail)
        binding.mediaTitle.text = entry.name
    }
}