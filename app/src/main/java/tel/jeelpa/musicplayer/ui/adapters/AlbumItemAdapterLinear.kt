package tel.jeelpa.musicplayer.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import tel.jeelpa.musicplayer.databinding.ItemAlbumEntryBinding
import tel.jeelpa.musicplayer.models.ItemSmallData

class AlbumItemAdapterLinear<OpaqueItemSmall : ItemSmallData>(
    private val onItemClick: (OpaqueItemSmall) -> Unit = {},
    private val onItemLongClick: (OpaqueItemSmall) -> Boolean = { false },
): GenericPagingAdapter<OpaqueItemSmall, ItemAlbumEntryBinding>(ItemSmallComparator()){
    override fun inflateCallback(
        layoutInflator: LayoutInflater,
        viewGroup: ViewGroup?,
        attachToParent: Boolean
    ): ItemAlbumEntryBinding {
        return ItemAlbumEntryBinding.inflate(layoutInflator, viewGroup, attachToParent)
    }

    override fun onBind(binding: ItemAlbumEntryBinding, entry: OpaqueItemSmall, position: Int) {
        binding.root.setOnClickListener { onItemClick(entry) }
        binding.root.setOnLongClickListener {
            onItemLongClick(entry)
        }
        binding.mediaIndex.text = (position + 1).toString()
        binding.mediaTitle.text = entry.title

        // TODO: add artist
        binding.mediaArtist.text = "ARTIST: TODO"
        // TODO: add length
        binding.mediaLength.text = "00:00"
    }
}