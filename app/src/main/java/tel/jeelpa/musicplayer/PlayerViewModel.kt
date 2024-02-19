package tel.jeelpa.musicplayer

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import tel.jeelpa.musicplayer.musicservices.GetCurrentClient
import tel.jeelpa.musicplayer.player.AppPlayer
import tel.jeelpa.musicplayer.player.FlowPlayerListener
import tel.jeelpa.musicplayer.player.models.toSong
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
    val player: AppPlayer,
    val listener: FlowPlayerListener,
    val currentService: GetCurrentClient
): ViewModel() {

    suspend fun playTrack(url: String)  {
        player.clearMediaItems()
        val playableLink = withContext(Dispatchers.IO) {
            currentService().first().getTrackClient(url).getMediaSource().first()
        }
        player.setMediaItem(playableLink.toSong())
    }
}
