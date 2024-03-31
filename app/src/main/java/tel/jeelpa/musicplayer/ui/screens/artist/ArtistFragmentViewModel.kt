package tel.jeelpa.musicplayer.ui.screens.artist

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
class ArtistFragmentViewModel @Inject constructor(
    currentClient: GetCurrentClient, savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val navArgs = ArtistFragmentArgs.fromSavedStateHandle(savedStateHandle)
    val artistDetails = navArgs.artistDetails

    val artistSongs = currentClient().transform {
        emitAll(
            it.getArtistClient().getArtist(artistDetails.id).getSongs()
        )
    }.flowOn(Dispatchers.IO).cachedIn(viewModelScope)

    val artistAlbums = currentClient().transform {
        emitAll(
            it.getArtistClient().getArtist(artistDetails.id).getAlbums()
        )
    }.flowOn(Dispatchers.IO).cachedIn(viewModelScope)
}