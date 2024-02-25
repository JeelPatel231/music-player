package tel.jeelpa.musicplayer.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import tel.jeelpa.musicplayer.models.toLazyAppTrack
import tel.jeelpa.musicplayer.musicservices.GetCurrentClient
import tel.jeelpa.musicplayer.utils.singleEmitOnIO
import javax.inject.Inject

@HiltViewModel
class SampleFragmentViewModel @Inject constructor(
    private val currentService: GetCurrentClient
) : ViewModel() {
    //    val sc = SCAlbum("https://soundcloud.com/majorlazer/sets/peace-is-the-mission", null, null)
    val songs = singleEmitOnIO {
        currentService()
            .first() // first value of the flow
            .getHomeFeedClient()
//        sc
            .getSongs(0, 0)
            .map { it.toLazyAppTrack(viewModelScope) }
    }

    fun search(query: String) = singleEmitOnIO {
        currentService().first()
            .getTrackClient()
            .search(query, 0, 0)
    }

}
