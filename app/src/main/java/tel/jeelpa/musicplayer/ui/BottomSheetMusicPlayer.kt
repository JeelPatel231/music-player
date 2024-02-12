package tel.jeelpa.musicplayer.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import tel.jeelpa.musicplayer.config.Configuration
import tel.jeelpa.musicplayer.config.toPx
import tel.jeelpa.musicplayer.databinding.BottomSheetPlayerBinding
import tel.jeelpa.musicplayer.databinding.BottomSheetQueueBinding
import tel.jeelpa.musicplayer.databinding.MiniPlayerBinding
import tel.jeelpa.musicplayer.player.AppPlayer
import tel.jeelpa.musicplayer.player.EventForwarder
import tel.jeelpa.musicplayer.player.PlayerListener
import tel.jeelpa.musicplayer.player.models.PlaybackState
import javax.inject.Inject

@AndroidEntryPoint
class BottomSheetMusicPlayerView(
    context: Context,
    attrSet: AttributeSet? = null,
    defStyleAttr: Int,
) : CoordinatorLayout(context, attrSet, defStyleAttr) {
    constructor(context: Context, attrSet: AttributeSet?) : this(context, attrSet, 0)
    @Inject lateinit var listenEventForwarder: EventForwarder
    @Inject lateinit var player: AppPlayer

    private val handleScrollCallback = object: BottomSheetBehavior.BottomSheetCallback() {
        override fun onStateChanged(bottomSheet: View, newState: Int) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                player.clearMediaItems()
                player.stop()
            }
        }

        override fun onSlide(bottomSheet: View, slideOffset: Float) {
            miniPlayerBinding.root.alpha = 1 - (slideOffset * MULTIPLIER).coerceIn(0F, 1F)
        }

    }

    private val queueScrollCallback = object: BottomSheetBehavior.BottomSheetCallback() {
        override fun onStateChanged(bottomSheet: View, newState: Int) {
            behavior.isDraggable = BottomSheetBehavior.STATE_COLLAPSED == newState
        }

        override fun onSlide(bottomSheet: View, slideOffset: Float) { /* Do Nothing */ }
    }

    private val openMiniPlayerOnMusicStart = object: PlayerListener {
        override fun onPlaybackStateChanged(playbackState: PlaybackState) {
            when(playbackState) {
                PlaybackState.Playing -> behavior.state = BottomSheetBehavior.STATE_COLLAPSED
                else -> Unit
            }
        }
    }


    private val bottomSheetPlayerBinding = BottomSheetPlayerBinding.inflate(LayoutInflater.from(context), this, true)
    private val miniPlayerBinding = MiniPlayerBinding.bind(bottomSheetPlayerBinding.miniPlayer.root)
    private val bottomSheetQueueBinding = BottomSheetQueueBinding.bind(bottomSheetPlayerBinding.bottomSheetQueue.root)

    private val behavior = BottomSheetBehavior.from(bottomSheetPlayerBinding.root)
    private val queueBehavior = BottomSheetBehavior.from(bottomSheetPlayerBinding.bottomSheetQueue.root)

    private fun <T : View> setupBottomSheets(behavior: BottomSheetBehavior<T>) {
        behavior.peekHeight = Configuration.PEEK_HEIGHT.toPx(context)
        behavior.isHideable = true
    }

    init {
        setupBottomSheets(behavior)
        setupBottomSheets(queueBehavior)

        behavior.addBottomSheetCallback(handleScrollCallback)
        queueBehavior.addBottomSheetCallback(queueScrollCallback)

        bottomSheetPlayerBinding.miniPlayer.playPauseBtn.setOnClickListener {
            if(player.isPlaying()) player.pause() else player.play()
        }

        listenEventForwarder.addListener(openMiniPlayerOnMusicStart)
    }

    companion object {
        private const val MULTIPLIER = 5
    }
}
