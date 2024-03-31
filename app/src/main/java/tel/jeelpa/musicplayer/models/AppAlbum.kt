package tel.jeelpa.musicplayer.models

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import tel.jeelpa.musicplayer.common.models.Album


/**
 * data is loaded eagerly from suspending functions
 *
 * and the lazy loading data is just a function
 * which gets called when needed.
 * */
suspend fun Album.toSmallMedia(): ItemSmallDataClass {

    val name = withContext(Dispatchers.IO) {
        getName()
    }
    val avatar = withContext(Dispatchers.IO) {
        getAlbumArt()
    }

    return ItemSmallDataClass(
        getUrl(),
        name,
        avatar,
    )
}
