package tel.jeelpa.musicplayer.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import coil.load
import tel.jeelpa.musicplayer.databinding.ItemMediaSmallBinding
import tel.jeelpa.musicplayer.models.ItemSmallData

// TODO: Convert to singleton object
class ItemSmallComparator<T: ItemSmallData>: DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.id == newItem.id
    }
}

class MediaItemAdapter<OpaqueItemSmall : ItemSmallData>(
    private val onItemClick: (OpaqueItemSmall) -> Unit = {},
    private val onItemLongClick: (OpaqueItemSmall) -> Boolean = { false },
): GenericPagingAdapter<OpaqueItemSmall, ItemMediaSmallBinding>(ItemSmallComparator()){
    override fun inflateCallback(
        layoutInflator: LayoutInflater,
        viewGroup: ViewGroup?,
        attachToParent: Boolean
    ): ItemMediaSmallBinding {
        return ItemMediaSmallBinding.inflate(layoutInflator, viewGroup, attachToParent)
    }

    override fun onBind(binding: ItemMediaSmallBinding, entry: OpaqueItemSmall, position: Int) {
        binding.root.setOnClickListener { onItemClick(entry) }
        binding.root.setOnLongClickListener {
            onItemLongClick(entry)
        }
        binding.mediaArt.load(entry.avatar)
        binding.mediaTitle.text = entry.title
    }
}