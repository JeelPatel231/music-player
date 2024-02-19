package tel.jeelpa.musicplayer

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import tel.jeelpa.musicplayer.databinding.ActivityMainBinding
import tel.jeelpa.musicplayer.databinding.BottomSheetPlayerBinding
import tel.jeelpa.musicplayer.databinding.BottomSheetQueueBinding
import tel.jeelpa.musicplayer.databinding.MiniPlayerBinding
import tel.jeelpa.musicplayer.player.models.PlaybackState
import tel.jeelpa.musicplayer.ui.SampleFragment
import tel.jeelpa.musicplayer.utils.observeFlow


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private val playerViewModel: PlayerViewModel by viewModels()
    private val player get() = playerViewModel.player
    private val listener get() = playerViewModel.listener

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sample = SampleFragment()

        supportFragmentManager.beginTransaction()
            .replace(binding.mainActivityContainer.id, sample)
            .commit()

        setupMiniPlayer()
    }


    private fun setupMiniPlayer() {
        val bottomPlayerSheet = BottomSheetPlayerBinding.bind(binding.root)
        val miniPlayer = MiniPlayerBinding.bind(bottomPlayerSheet.root)
        val queueSheet = BottomSheetQueueBinding.bind(bottomPlayerSheet.root)
        val MULTIPLIER = 5


        miniPlayer.playPauseBtn.setOnClickListener {
            if (player.isPlaying()) player.pause() else player.play()
        }

        val bottomPlayerSheetBehavior =
            BottomSheetBehavior.from(bottomPlayerSheet.bottomPlayerSheet)
        val queueSheetBehavior = BottomSheetBehavior.from(queueSheet.bottomSheetQueue)


        bottomPlayerSheetBehavior
            .addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    /* DO NOTHING */
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    binding.bottomNav.translationY =
                        (binding.bottomNav.height * slideOffset).coerceAtLeast(0F)
                }
            })

        val handleScrollCallback = object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    player.clearMediaItems()
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                miniPlayer.miniPlayer.alpha = 1 - (slideOffset * MULTIPLIER).coerceIn(0F, 1F)
            }

        }

        val queueScrollCallback = object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                bottomPlayerSheetBehavior.isDraggable =
                    BottomSheetBehavior.STATE_COLLAPSED == newState
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                /* Do Nothing */
            }
        }

        listener.playbackState.observeFlow(this) {
            if(it == PlaybackState.Idle || it == PlaybackState.Stopped)
                bottomPlayerSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN

            if(it == PlaybackState.Buffering && bottomPlayerSheetBehavior.state == BottomSheetBehavior.STATE_HIDDEN)
                bottomPlayerSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED

        }


        listener.isPlaying.observeFlow(this) {
            val btnImage =
                if (it) R.drawable.round_pause_24 else R.drawable.round_play_arrow_24
            miniPlayer.playPauseBtn.load(btnImage)
        }

        bottomPlayerSheetBehavior.addBottomSheetCallback(handleScrollCallback)
        queueSheetBehavior.addBottomSheetCallback(queueScrollCallback)

    }
}
