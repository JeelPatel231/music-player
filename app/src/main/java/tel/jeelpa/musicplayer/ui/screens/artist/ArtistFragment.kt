package tel.jeelpa.musicplayer.ui.screens.artist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.map
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import tel.jeelpa.musicplayer.PlayerViewModel
import tel.jeelpa.musicplayer.databinding.FragmentArtistBinding
import tel.jeelpa.musicplayer.models.ItemSmallData
import tel.jeelpa.musicplayer.models.toDataClass
import tel.jeelpa.musicplayer.models.toLazyAppTrack
import tel.jeelpa.musicplayer.models.toSmallMedia
import tel.jeelpa.musicplayer.ui.adapters.MediaItemAdapter
import tel.jeelpa.musicplayer.ui.adapters.MediaItemAdapterLinear
import tel.jeelpa.musicplayer.ui.utils.autoCleared
import tel.jeelpa.musicplayer.ui.utils.getNavController
import tel.jeelpa.musicplayer.ui.utils.setupHorizontalGridWithAdapter
import tel.jeelpa.musicplayer.ui.utils.setupHorizontalWithAdapter
import tel.jeelpa.musicplayer.utils.observeFlow

@AndroidEntryPoint
class ArtistFragment: Fragment() {
    private var binding: FragmentArtistBinding by autoCleared()

    private val artistViewModel: ArtistFragmentViewModel by viewModels()
    private val playerViewModel: PlayerViewModel by activityViewModels()

    private fun openAlbum(details: ItemSmallData){
        val direction = ArtistFragmentDirections.toAlbumFragment(details.toDataClass())
        requireActivity().getNavController().navigate(direction)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentArtistBinding.inflate(inflater, container, false)

        binding.artistAvatar.load(artistViewModel.artistDetails.avatar)
        binding.artistName.text = artistViewModel.artistDetails.title

        // songs
        val songsAdapter = MediaItemAdapterLinear(
            onItemClick = playerViewModel::playTrack,
            onItemLongClick = playerViewModel::addToQueue,
        )
        artistViewModel.artistSongs.observeFlow(this) { tracks ->
            songsAdapter.submitData(tracks.map { it.toLazyAppTrack(lifecycleScope) })
        }
        binding.songs.setupHorizontalGridWithAdapter(songsAdapter)

        // albums
        val albumsAdapter = MediaItemAdapter<ItemSmallData>(
            onItemClick = { openAlbum(it) },
        )
        artistViewModel.artistAlbums.observeFlow(this) { albums ->
            albumsAdapter.submitData(albums.map { it.toSmallMedia() })
        }
        binding.albums.setupHorizontalWithAdapter(albumsAdapter)

        return binding.root
    }

}