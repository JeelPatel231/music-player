package tel.jeelpa.musicplayer.ui.utils

import androidx.paging.PagingData

fun <E: Any> List<E>.toPagedData() = PagingData.from(this)