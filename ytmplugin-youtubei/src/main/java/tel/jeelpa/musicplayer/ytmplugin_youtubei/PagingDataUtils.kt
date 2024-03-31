package tel.jeelpa.musicplayer.ytmplugin_youtubei

import androidx.paging.PagingData

fun <E: Any> List<E>.toPagedData() = PagingData.from(this)