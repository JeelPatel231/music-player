package tel.jeelpa.musicplayer.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import coil.load
import tel.jeelpa.musicplayer.databinding.ItemMediaSmallBinding
import tel.jeelpa.musicplayer.models.Track

class MediaItemAdapter(
    private val onItemClick: (Track) -> Unit,
): GenericListAdapter<Track, ItemMediaSmallBinding>(){
    override fun inflateCallback(
        layoutInflator: LayoutInflater,
        viewGroup: ViewGroup?,
        attachToParent: Boolean
    ): ItemMediaSmallBinding {
        return ItemMediaSmallBinding.inflate(layoutInflator, viewGroup, attachToParent)
    }

    override fun onBind(binding: ItemMediaSmallBinding, entry: Track, position: Int) {
        binding.root.setOnClickListener { onItemClick(entry) }
        binding.mediaArt.load(entry.thumbnail)
        binding.mediaTitle.text = entry.name
    }
}