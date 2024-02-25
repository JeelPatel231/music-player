package tel.jeelpa.musicplayer.ui.screens

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import tel.jeelpa.musicplayer.PlayerViewModel
import tel.jeelpa.musicplayer.databinding.FragmentHomeBinding
import tel.jeelpa.musicplayer.databinding.LayoutHomeRowBinding
import tel.jeelpa.musicplayer.models.toAppTrack
import tel.jeelpa.musicplayer.ui.adapters.MediaItemAdapter
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

    private val searchAdapter by lazy {
        MediaItemAdapter(
            onItemClick = playerViewModel::playTrack,
            onItemLongClick = {
                Snackbar.make(
                    binding.root.rootView,
                    "Added ${it.name} to queue",
                    Snackbar.LENGTH_SHORT
                ).show()
                playerViewModel.addToQueue(it)
            },
        )
    }

    private fun observeSearchFlow(query: String) {
        fragmentViewModel.search(query).observeFlow(viewLifecycleOwner) {
            searchAdapter.submitList(it.map { a -> a.toAppTrack() })
        }
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

        binding.searchView.editText.setOnEditorActionListener { textView, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH || keyEvent.keyCode == KeyEvent.KEYCODE_ENTER) {
                textView.text.toString().nullOnBlank()?.let {
                    observeSearchFlow(it)
                    return@setOnEditorActionListener true
                }
            }
            false
        }

        binding.searchRecycler.apply {
            layoutManager = GridLayoutManager(requireContext(), 3, GridLayoutManager.VERTICAL, false)
            adapter = searchAdapter
        }

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

fun String.nullOnBlank(): String? {
    if (isNullOrBlank()) return null
    return this
}