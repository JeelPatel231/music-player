package tel.jeelpa.musicplayer.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import coil.load
import tel.jeelpa.musicplayer.databinding.ItemMediaSmallLinearBinding
import tel.jeelpa.musicplayer.models.ItemSmallData

class MediaItemAdapterLinear<OpaqueItemSmall : ItemSmallData>(
    private val onItemClick: (OpaqueItemSmall) -> Unit = {},
    private val onItemLongClick: (OpaqueItemSmall) -> Boolean = { false },
): GenericPagingAdapter<OpaqueItemSmall, ItemMediaSmallLinearBinding>(ItemSmallComparator()){
    override fun inflateCallback(
        layoutInflator: LayoutInflater,
        viewGroup: ViewGroup?,
        attachToParent: Boolean
    ): ItemMediaSmallLinearBinding {
        return ItemMediaSmallLinearBinding.inflate(layoutInflator, viewGroup, attachToParent)
    }

    override fun onBind(
        binding: ItemMediaSmallLinearBinding,
        entry: OpaqueItemSmall,
        position: Int
    ) {
        binding.root.setOnClickListener { onItemClick(entry) }
        binding.root.setOnLongClickListener {
            onItemLongClick(entry)
        }
        binding.mediaArt.load(entry.avatar)
        binding.mediaTitle.text = entry.title

        // TODO: add artist
        binding.mediaArtist.text = "ARTIST: TODO"
    }
}