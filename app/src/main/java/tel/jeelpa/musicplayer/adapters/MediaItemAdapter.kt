package tel.jeelpa.musicplayer.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import coil.load
import tel.jeelpa.musicplayer.databinding.ItemMediaSmallBinding
import tel.jeelpa.musicplayer.models.AppTrack

class MediaItemAdapter(
    private val onItemClick: (AppTrack) -> Unit = {},
    private val onItemLongClick: (AppTrack) -> Boolean = { false },
): GenericListAdapter<AppTrack, ItemMediaSmallBinding>(){
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