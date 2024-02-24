package tel.jeelpa.musicplayer.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

fun <T> singleEmitOnIO(callback: suspend () -> T) =
    flow { emit(callback()) }.flowOn(Dispatchers.IO)
