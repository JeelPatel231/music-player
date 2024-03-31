package tel.jeelpa.musicplayer.ui.screens.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import tel.jeelpa.musicplayer.PlayerViewModel
import tel.jeelpa.musicplayer.databinding.FragmentHomeBinding
import tel.jeelpa.musicplayer.models.ItemSmallDataClass
import tel.jeelpa.musicplayer.models.LazyAppTrack
import tel.jeelpa.musicplayer.ui.adapters.HomeFeedEntryAdapter
import tel.jeelpa.musicplayer.ui.adapters.ItemClickUseCase
import tel.jeelpa.musicplayer.ui.adapters.OnItemClick
import tel.jeelpa.musicplayer.ui.utils.autoCleared
import tel.jeelpa.musicplayer.ui.utils.getNavController
import tel.jeelpa.musicplayer.ui.utils.setupVerticalWithAdapter
import tel.jeelpa.musicplayer.utils.observeFlow

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var binding: FragmentHomeBinding by autoCleared()

    private val playerViewModel: PlayerViewModel by activityViewModels()
    private val fragmentViewModel: HomeFragmentViewModel by viewModels()

    private val trackClickUseCase = object : OnItemClick<LazyAppTrack> {
        override fun onClick(entry: LazyAppTrack) = playerViewModel.playTrack(entry)

        override fun onLongClick(entry: LazyAppTrack): Boolean {
            playerViewModel.addToQueue(entry)
            return true
        }
    }

    private val artistClickUseCase = object : OnItemClick<ItemSmallDataClass> {
        override fun onClick(entry: ItemSmallDataClass) {
            val direction = HomeFragmentDirections.toArtistFragment(entry)
            requireActivity().getNavController().navigate(direction)
        }

        override fun onLongClick(entry: ItemSmallDataClass): Boolean = false
    }

    private val albumClickUseCase = object : OnItemClick<ItemSmallDataClass> {
        override fun onClick(entry: ItemSmallDataClass) {
            val direction = HomeFragmentDirections.toAlbumFragment(entry)
            requireActivity().getNavController().navigate(direction)
        }

        override fun onLongClick(entry: ItemSmallDataClass): Boolean = false
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        val onClickUseCase =
            ItemClickUseCase(trackClickUseCase, artistClickUseCase, albumClickUseCase)

        val feedAdapter = HomeFeedEntryAdapter(onClickUseCase)

        binding.feedRecycler.setupVerticalWithAdapter(feedAdapter)

        fragmentViewModel.feed.observeFlow(viewLifecycleOwner) {
            feedAdapter.submitData(it)
        }

        return binding.root
    }
}
