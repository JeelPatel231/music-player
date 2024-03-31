package tel.jeelpa.musicplayer.ytmplugin_youtubei

import dev.toastbits.ytmkt.impl.youtubei.YoutubeiApi
import dev.toastbits.ytmkt.model.external.ThumbnailProvider
import dev.toastbits.ytmkt.model.external.mediaitem.YtmArtist
import dev.toastbits.ytmkt.model.external.mediaitem.YtmPlaylist
import dev.toastbits.ytmkt.model.external.mediaitem.YtmSong

fun YtmPlaylist.toAlbum(api: YoutubeiApi): YoutubeiAlbum {
    return api.getAlbum(
        id,
        name ?: "Unknown Artist",
        artist?.toArtist(api),
        thumbnail_provider?.getThumbnailUrl(ThumbnailProvider.Quality.LOW) ?: "",
    )
}

fun YtmArtist.toArtist(api: YoutubeiApi): YoutubeiArtist {
    return api.getArtist(
        id,
        name!!,
        thumbnail_provider?.getThumbnailUrl(ThumbnailProvider.Quality.LOW)
    )
}

fun YtmSong.toTrack(api: YoutubeiApi): YoutubeiTrack {
    return api.getTrack(
        id,
        name!!,
        thumbnail_provider?.getThumbnailUrl(ThumbnailProvider.Quality.LOW)!!,
    )
}
