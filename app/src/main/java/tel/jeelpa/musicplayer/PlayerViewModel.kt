package tel.jeelpa.musicplayer

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import tel.jeelpa.musicplayer.models.AppTrack
import tel.jeelpa.musicplayer.player.AppPlayer
import tel.jeelpa.musicplayer.player.FlowPlayerListener
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
    val player: AppPlayer,
    val listener: FlowPlayerListener,
) : ViewModel() {
    fun playAtPosition(idx: Int) =
        player.seekToMediaItem(idx)

    fun addToQueue(appTrack: AppTrack): Boolean {
        player.addMediaItem(appTrack)
        player.play()
        return true
    }

    fun playTrack(appTrack: AppTrack) {
        player.setMediaItem(appTrack)
        player.play() // player doesnt not play automatically if it was paused
    }
}
