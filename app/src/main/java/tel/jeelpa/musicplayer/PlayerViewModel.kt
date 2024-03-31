package tel.jeelpa.musicplayer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.Player
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import tel.jeelpa.musicplayer.models.LazyAppTrack
import tel.jeelpa.musicplayer.models.toMediaItem
import tel.jeelpa.musicplayer.player.FlowPlayerListener
import javax.inject.Inject

data class SnackBarEvent(val msg: String)

@HiltViewModel
class PlayerViewModel @Inject constructor(
    val player: Player,
    val listener: FlowPlayerListener,
) : ViewModel() {

    private val _snackBarEvents = MutableSharedFlow<SnackBarEvent>()
    val snackBarEvents = _snackBarEvents.asSharedFlow()

    fun playAtPosition(idx: Int) =
        player.seekToDefaultPosition(idx)

    fun addToQueue(appTrack: LazyAppTrack): Boolean {
        viewModelScope.launch {
            player.addMediaItem(appTrack.toMediaItem())
            player.play()
            _snackBarEvents.emit(SnackBarEvent("Added ${appTrack.title} to queue"))
        }
        return true
    }

    fun playTrack(appTrack: LazyAppTrack) {
        viewModelScope.launch {
            player.setMediaItem(appTrack.toMediaItem())
            player.play() // player doesnt not play automatically if it was paused
        }
    }
}
