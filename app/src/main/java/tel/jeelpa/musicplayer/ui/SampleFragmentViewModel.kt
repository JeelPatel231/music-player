package tel.jeelpa.musicplayer.ui

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import tel.jeelpa.musicplayer.models.toTrack
import tel.jeelpa.musicplayer.musicservices.GetCurrentClient
import tel.jeelpa.musicplayer.utils.singleEmitOnIO
import javax.inject.Inject

@HiltViewModel
class SampleFragmentViewModel @Inject constructor(
    private val currentService: GetCurrentClient
) : ViewModel() {

    fun getSong() = singleEmitOnIO {
        currentService()
            .first() // first value of the flow
            .getAlbumClient("https://music.youtube.com/playlist?list=OLAK5uy_l4UqNJCpAF3kNaV37LHdRc_A07MmVdiSU")
//            .getAlbumClient("https://soundcloud.com/majorlazer/sets/peace-is-the-mission-extended")
            .getSongs(0, 0)
            .map { it.toTrack() }
    }
}

