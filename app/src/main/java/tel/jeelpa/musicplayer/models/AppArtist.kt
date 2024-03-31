package tel.jeelpa.musicplayer.models

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import tel.jeelpa.musicplayer.common.models.Artist


/**
 * data is loaded eagerly from suspending functions
 *
 * and the lazy loading data is just a function
 * which gets called when needed.
 * */
suspend fun Artist.toSmallMedia(): ItemSmallDataClass {

    val name = withContext(Dispatchers.IO) {
        getName()
    }
    val avatar = withContext(Dispatchers.IO) {
        getAvatar()
    }

    return ItemSmallDataClass(
        getUrl(),
        name,
        avatar,
    )
}
