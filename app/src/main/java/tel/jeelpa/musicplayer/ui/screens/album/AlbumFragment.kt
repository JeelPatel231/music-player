package tel.jeelpa.musicplayer.ui.screens.album

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
import tel.jeelpa.musicplayer.databinding.FragmentAlbumBinding
import tel.jeelpa.musicplayer.models.toLazyAppTrack
import tel.jeelpa.musicplayer.ui.adapters.AlbumItemAdapterLinear
import tel.jeelpa.musicplayer.ui.utils.autoCleared
import tel.jeelpa.musicplayer.ui.utils.setupVerticalWithAdapter
import tel.jeelpa.musicplayer.utils.observeFlow


@AndroidEntryPoint
class AlbumFragment: Fragment() {
    private var binding: FragmentAlbumBinding by autoCleared()
    private val albumViewModel: AlbumFragmentViewModel by viewModels()
    private val playerViewModel: PlayerViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAlbumBinding.inflate(inflater, container, false)

        binding.albumName.text = albumViewModel.albumDetails.title
        binding.albumArt.load(albumViewModel.albumDetails.avatar)

        val songAdapter = AlbumItemAdapterLinear(
            onItemClick = playerViewModel::playTrack,
            onItemLongClick = playerViewModel::addToQueue,
        )
        albumViewModel.songs.observeFlow(viewLifecycleOwner) { tracks ->
            songAdapter.submitData(tracks.map { it.toLazyAppTrack(lifecycleScope) })
        }
        binding.songs.setupVerticalWithAdapter(songAdapter)

        return binding.root
    }
}