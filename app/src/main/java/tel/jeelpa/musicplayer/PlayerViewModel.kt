package tel.jeelpa.musicplayer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import tel.jeelpa.musicplayer.models.LazyAppTrack
import tel.jeelpa.musicplayer.models.loadEager
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

    fun addToQueue(appTrack: LazyAppTrack): Boolean {
        viewModelScope.launch {
            player.addMediaItem(appTrack.loadEager())
            player.play()
        }
        return true
    }

    fun playTrack(appTrack: LazyAppTrack) {
        viewModelScope.launch {
            player.setMediaItem(appTrack.loadEager())
            player.play() // player doesnt not play automatically if it was paused
        }
    }
}
