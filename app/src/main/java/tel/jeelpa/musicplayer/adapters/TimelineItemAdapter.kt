package tel.jeelpa.musicplayer.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import coil.load
import tel.jeelpa.musicplayer.databinding.ItemPlaylistItemBinding
import tel.jeelpa.musicplayer.models.AppTrack


object TrackDifferentiatorWithCurrent : DiffUtil.ItemCallback<TrackWithCurrent>() {
    override fun areItemsTheSame(oldItem: TrackWithCurrent, newItem: TrackWithCurrent): Boolean {
        return oldItem.first.id == newItem.first.id
    }

    override fun areContentsTheSame(oldItem: TrackWithCurrent, newItem: TrackWithCurrent): Boolean {
        // Strictly assume that same ID WILL ALWAYS have same properties
        return oldItem.first.id == newItem.first.id && oldItem.second == newItem.second
    }
}

typealias TrackWithCurrent = Pair<
        AppTrack,      /*Track Info*/
        Boolean,    /* Is Currently Playing? */
        >

class TimelineItemAdapter(
    private val onItemClick: (Int) -> Unit,
) : GenericListAdapter<TrackWithCurrent, ItemPlaylistItemBinding>(
    TrackDifferentiatorWithCurrent
) {
    override fun inflateCallback(
        layoutInflator: LayoutInflater,
        viewGroup: ViewGroup?,
        attachToParent: Boolean
    ): ItemPlaylistItemBinding {
        return ItemPlaylistItemBinding.inflate(layoutInflator, viewGroup, attachToParent)
    }

    override fun onBind(binding: ItemPlaylistItemBinding, entry: TrackWithCurrent, position: Int) {
        binding.root.setOnClickListener { onItemClick(position) }
        val (track, isPlaying) = entry
        binding.playlistItemArt.load(track.thumbnail)
        binding.playlistItemTitle.text = track.name
        binding.playlistItemAuthor.text = track.artist

        binding.isPlayingIndicator.visibility = if (isPlaying) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }
}