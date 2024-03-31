package tel.jeelpa.musicplayer.ui.screens.home

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
class HomeFragmentViewModel @Inject constructor(
    private val currentService: GetCurrentClient
) : ViewModel() {
    val feed = currentService().transform {
        emitAll(it.getHomeFeedClient().getHomeFeed())
    }.flowOn(Dispatchers.IO).cachedIn(viewModelScope)
}
