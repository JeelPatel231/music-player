package tel.jeelpa.musicplayer.common

import tel.jeelpa.musicplayer.common.clients.AlbumClient
import tel.jeelpa.musicplayer.common.clients.ArtistClient
import tel.jeelpa.musicplayer.common.clients.HomeFeedClient
import tel.jeelpa.musicplayer.common.clients.TrackClient

interface ClientsHolder {
    fun getHomeFeedClient(): HomeFeedClient
    fun getAlbumClient(id: String): AlbumClient
    fun getArtistClient(id: String): ArtistClient
    fun getTrackClient(id: String): TrackClient
}