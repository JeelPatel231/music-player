package tel.jeelpa.musicplayer.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import tel.jeelpa.musicplayer.databinding.SampleFragmentBinding
import tel.jeelpa.musicplayer.player.AppPlayer
import tel.jeelpa.musicplayer.player.models.Song
import javax.inject.Inject

@AndroidEntryPoint
class SampleFragment : Fragment() {
    private var _binding: SampleFragmentBinding? = null
    private val binding get() = _binding!!

    @Inject lateinit var player: AppPlayer

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SampleFragmentBinding.inflate(inflater, container, false)

        binding.btn.setOnClickListener {
            val song =
                Song.HttpUrl("https://file-examples.com/storage/fe63e96e0365c0e1e99a842/2017/11/file_example_MP3_5MG.mp3")
            player.setMediaItem(song)
            player.play()
        }

        return binding.root
    }
}