package tel.jeelpa.musicplayer.ytmplugin_youtubei

import dev.toastbits.ytmkt.impl.youtubei.YoutubeiApi
import tel.jeelpa.musicplayer.common.ClientsHolder
import tel.jeelpa.musicplayer.common.clients.AlbumClient
import tel.jeelpa.musicplayer.common.clients.ArtistClient
import tel.jeelpa.musicplayer.common.clients.HomeFeedClient
import tel.jeelpa.musicplayer.common.clients.TrackClient
import tel.jeelpa.musicplayer.ytmplugin_youtubei.clients.YoutubeiAlbumClient
import tel.jeelpa.musicplayer.ytmplugin_youtubei.clients.YoutubeiArtistClient
import tel.jeelpa.musicplayer.ytmplugin_youtubei.clients.YoutubeiTrackClient

class YoutubeiPlugin: ClientsHolder {
    private val apiClient = YoutubeiApi()

    override fun getName(): String = "tel.jeelpa.musicplayer.Youtubei"

    override fun getHomeFeedClient(): HomeFeedClient =
        YoutubeiHomeFeedClient(apiClient)

    override fun getAlbumClient(): AlbumClient {
        return YoutubeiAlbumClient(apiClient)
    }

    override fun getArtistClient(): ArtistClient {
        return YoutubeiArtistClient(apiClient)
    }

    override fun getTrackClient(): TrackClient {
        return YoutubeiTrackClient(apiClient)
    }
}