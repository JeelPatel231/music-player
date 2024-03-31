package tel.jeelpa.musicplayer.ui.screens.album

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.transform
import tel.jeelpa.musicplayer.musicservices.GetCurrentClient
import javax.inject.Inject

@HiltViewModel
class AlbumFragmentViewModel @Inject constructor(
    getCurrentClient: GetCurrentClient, savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val navArgs = AlbumFragmentArgs.fromSavedStateHandle(savedStateHandle)

    val albumDetails = navArgs.albumDetails

    val songs = getCurrentClient().transform {
        emitAll(
            it.getAlbumClient().getAlbum(albumDetails.id).getSongs()
        )
    }.flowOn(Dispatchers.IO).cachedIn(viewModelScope)

}