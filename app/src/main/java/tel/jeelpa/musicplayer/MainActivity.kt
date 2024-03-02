package tel.jeelpa.musicplayer

import android.os.Bundle
import android.text.format.DateUtils
import android.view.View
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_COLLAPSED
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_HIDDEN
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.filterNotNull
import tel.jeelpa.musicplayer.databinding.ActivityMainBinding
import tel.jeelpa.musicplayer.databinding.BottomSheetPlayerBinding
import tel.jeelpa.musicplayer.databinding.BottomSheetQueueBinding
import tel.jeelpa.musicplayer.databinding.FullPlayerBinding
import tel.jeelpa.musicplayer.databinding.MiniPlayerBinding
import tel.jeelpa.musicplayer.player.models.Duration
import tel.jeelpa.musicplayer.player.models.PlaybackState
import tel.jeelpa.musicplayer.ui.adapters.TimelineItemAdapter
import tel.jeelpa.musicplayer.ui.screens.SampleFragment
import tel.jeelpa.musicplayer.utils.BottomSheetBackPress.getBackPressedHandler
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


        ViewCompat.setOnApplyWindowInsetsListener(binding.mainActivityContainer) { view, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.updatePadding(insets.left, insets.top, insets.right, insets.bottom)

            WindowInsetsCompat.CONSUMED
        }

        val sample = SampleFragment()

        supportFragmentManager.beginTransaction()
            .replace(binding.mainActivityContainer.id, sample)
            .commit()

        setupMiniPlayer()

    }


    private fun setupMiniPlayer() {
        val bottomPlayerSheet = BottomSheetPlayerBinding.bind(binding.root)
        val miniPlayer = MiniPlayerBinding.bind(bottomPlayerSheet.root)
        val expandedPlayer = FullPlayerBinding.bind(bottomPlayerSheet.playerContainer)
        val queueSheet = BottomSheetQueueBinding.bind(bottomPlayerSheet.root)

        miniPlayer.playPause.setOnClickListener {
            if (player.isPlaying()) player.pause() else player.play()
        }

        expandedPlayer.expandedPlayPause.setOnClickListener {
            if (player.isPlaying()) player.pause() else player.play()
        }

        expandedPlayer.nextBtn.setOnClickListener {
            player.next()
            // if player is paused and "next" is called,
            // it changes tracks but its still paused.
            player.play()
        }

        expandedPlayer.prevBtn.setOnClickListener {
            player.previous()
            player.play()
        }

//        queueSheet.playlistClear.setOnClickListener {
//            player.clearMediaItems()
//        }

        val bottomPlayerSheetBehavior =
            BottomSheetBehavior.from(bottomPlayerSheet.bottomPlayerSheet)
        val queueSheetBehavior = BottomSheetBehavior.from(queueSheet.bottomQueueSheet)


        val miniPlayerHeight = resources.getDimension(R.dimen.mini_player_height)
        // set the peekHeight when BottomNavBar is laid out
        binding.bottomNav.post {
            bottomPlayerSheetBehavior.peekHeight =
                (binding.bottomNav.height + miniPlayerHeight).toInt()
        }

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
                if (newState == STATE_HIDDEN) {
                    player.clearMediaItems()
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                bottomPlayerSheet.playerContainer.translationY =
                    miniPlayerHeight * -slideOffset.coerceAtLeast(0F)
//                miniPlayer.miniPlayer.alpha = 1 - (slideOffset * 5).coerceIn(0F, 1F)
            }

        }

        val queueScrollCallback = object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                /* disable dragging of parent bottom sheet when child is expanded */
                bottomPlayerSheetBehavior.isDraggable =
                    STATE_COLLAPSED == newState
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                /* Do Nothing */
            }
        }

        expandedPlayer.expandedProgress.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            private fun getElapsedSeconds(seekBar: SeekBar): Long {
                val progress = seekBar.progress
                val duration = when(val dur = player.getDuration()) {
                    is Duration.Known -> dur.length
                    is Duration.Unknown -> return 0L
                }

                return (progress/100F * duration).toLong()
            }
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                expandedPlayer.elapsedTime.text = DateUtils.formatElapsedTime(getElapsedSeconds(seekBar)/1000)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                player.pause()
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                player.seekTo(getElapsedSeconds(seekBar))
                player.play()
            }
        })

        listener.listenToCurrentPosition().observeFlow(this) {
            val dur = player.getDuration().unwrap()
            val progress = if(dur == 0L) 0F else (it/dur.toFloat()) * 100
            expandedPlayer.elapsedTime.text = DateUtils.formatElapsedTime(it/1000)
            expandedPlayer.durationTime.text = DateUtils.formatElapsedTime(dur/1000)

            expandedPlayer.expandedProgress.setProgress(progress.toInt(), true)
            miniPlayer.miniPlayerProgress.setProgress(progress.toInt(), true)
        }

        listener.playbackState.observeFlow(this) {
            if (it == PlaybackState.Idle || it == PlaybackState.Stopped)
                bottomPlayerSheetBehavior.state = STATE_HIDDEN

            if (it == PlaybackState.Buffering && bottomPlayerSheetBehavior.state == STATE_HIDDEN)
                bottomPlayerSheetBehavior.state = STATE_COLLAPSED

        }

        val timelineAdapter = TimelineItemAdapter {
            playerViewModel.playAtPosition(it)
        }
        queueSheet.playlistRecycler.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = timelineAdapter
        }
//        listener.timeline.observeFlow(this) {
//            timelineAdapter.submitList(it)
//        }

        listener.playbackState.observeFlow(this) {
            if(it == PlaybackState.Buffering) {
                expandedPlayer.expandedLoadingIndicator.visibility = View.VISIBLE
                expandedPlayer.expandedPlayPause.visibility = View.GONE
                ///
                miniPlayer.loadingIndicator.visibility = View.VISIBLE
                miniPlayer.playPause.visibility = View.GONE
            } else {
                expandedPlayer.expandedLoadingIndicator.visibility = View.GONE
                expandedPlayer.expandedPlayPause.visibility = View.VISIBLE
                //
                miniPlayer.loadingIndicator.visibility = View.GONE
                miniPlayer.playPause.visibility = View.VISIBLE
            }
        }

        listener.currentMediaItem.filterNotNull().observeFlow(this) {
            val thumb = it.thumbnail
            val artist = it.artist
            val name = it.name

            expandedPlayer.expandedAlbumArt.load(thumb)
            miniPlayer.albumArt.load(thumb)

            expandedPlayer.expandedAuthorText.text = artist
            miniPlayer.authorText.text = artist

            expandedPlayer.expandedTitleText.text = name
            miniPlayer.trackText.text = name

            // update progress bar immediately
            miniPlayer.miniPlayerProgress.setProgress(player.getProgress().toInt(), true)
            expandedPlayer.expandedProgress.setProgress(player.getProgress().toInt(), true)

            // update prev and next buttons
            expandedPlayer.nextBtn.isEnabled = player.hasNextMediaItem()
            expandedPlayer.prevBtn.isEnabled = player.hasPreviousMediaItem()

            // update currentPlaying when track is changed
            val currentPlaying = player.getCurrentMediaItemIndex()
            val mapped = player.getTimeline().mapIndexed { idx, track -> Pair(track, idx == currentPlaying) }
            timelineAdapter.submitList(mapped)
        }

        listener.timeline.observeFlow(this) { tracks ->
            expandedPlayer.nextBtn.isEnabled = player.hasNextMediaItem()
            expandedPlayer.prevBtn.isEnabled = player.hasPreviousMediaItem()

            val currentPlaying = player.getCurrentMediaItemIndex()
            val mapped = tracks.mapIndexed { idx, track -> Pair(track, idx == currentPlaying) }
            timelineAdapter.submitList(mapped)
        }

        listener.isPlaying.observeFlow(this) {
            val btnImage =
                if (it) R.drawable.round_pause_24 else R.drawable.round_play_arrow_24
            val newDrawable =
                ResourcesCompat.getDrawable(resources, btnImage, theme)

            miniPlayer.playPause.background = newDrawable
            expandedPlayer.expandedPlayPause.background = newDrawable
        }

        bottomPlayerSheetBehavior.addBottomSheetCallback(handleScrollCallback)
        queueSheetBehavior.addBottomSheetCallback(queueScrollCallback)


        val playerSheetBackHandler = getBackPressedHandler(bottomPlayerSheetBehavior, true)
        val queueSheetBackHandler = getBackPressedHandler(queueSheetBehavior, false)

        onBackPressedDispatcher.addCallback(this, playerSheetBackHandler)
        onBackPressedDispatcher.addCallback(this, queueSheetBackHandler)
    }
}
