package tel.jeelpa.musicplayer.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import tel.jeelpa.musicplayer.PlayerViewModel
import tel.jeelpa.musicplayer.adapters.MediaItemAdapter
import tel.jeelpa.musicplayer.databinding.SampleFragmentBinding
import tel.jeelpa.musicplayer.utils.observeFlow

@AndroidEntryPoint
class SampleFragment : Fragment() {
    private var _binding: SampleFragmentBinding? = null
    private val binding get() = _binding!!

    private val playerViewModel: PlayerViewModel by activityViewModels()
    private val fragmentViewModel: SampleFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SampleFragmentBinding.inflate(inflater, container, false)

        val songAdapter = MediaItemAdapter {
            lifecycleScope.launch {
                playerViewModel.playTrack(it.url)
            }
        }

        fragmentViewModel.getSong().observeFlow(this) {
            songAdapter.submitList(it)
        }

        binding.songRecycler.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = songAdapter
        }

        return binding.root
    }
}