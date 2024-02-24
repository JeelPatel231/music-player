package tel.jeelpa.musicplayer.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import tel.jeelpa.musicplayer.PlayerViewModel
import tel.jeelpa.musicplayer.adapters.MediaItemAdapter
import tel.jeelpa.musicplayer.databinding.FragmentHomeBinding
import tel.jeelpa.musicplayer.databinding.LayoutHomeRowBinding
import tel.jeelpa.musicplayer.utils.observeFlow

@AndroidEntryPoint
class SampleFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val playerViewModel: PlayerViewModel by activityViewModels()
    private val fragmentViewModel: SampleFragmentViewModel by viewModels()

    private fun createRow(): LayoutHomeRowBinding {
        val row = LayoutHomeRowBinding.inflate(layoutInflater, binding.linearLayoutContainer, false)
        binding.linearLayoutContainer.addView(row.root)
        return row
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val songAdapter = MediaItemAdapter(
            onItemClick = playerViewModel::playTrack,
            onItemLongClick = {
                Snackbar.make(binding.root.rootView, "Added ${it.name} to queue", Snackbar.LENGTH_SHORT).show()
                playerViewModel.addToQueue(it)
            },
        )

        fragmentViewModel.songs.observeFlow(viewLifecycleOwner) {
            songAdapter.submitList(it)
        }

        val trendingRow = createRow()
        trendingRow.headlineText.text = "Quick Picks"
        trendingRow.rowRecycler.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = songAdapter
        }

        return binding.root
    }
}
