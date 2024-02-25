package tel.jeelpa.musicplayer.ui.screens

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import tel.jeelpa.musicplayer.models.toAppTrack
import tel.jeelpa.musicplayer.musicservices.GetCurrentClient
import tel.jeelpa.musicplayer.utils.singleEmitOnIO
import javax.inject.Inject

@HiltViewModel
class SampleFragmentViewModel @Inject constructor(
    private val currentService: GetCurrentClient
) : ViewModel() {

    val songs = singleEmitOnIO {
        currentService()
            .first() // first value of the flow
            .getHomeFeedClient()
            .getSongs(0, 0)
            .map { it.toAppTrack() }
    }

    fun search(query: String) = singleEmitOnIO {
        currentService().first()
            .getTrackClient()
            .search(query, 0, 0)
    }

}
