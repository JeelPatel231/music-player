package tel.jeelpa.musicplayer.common

import tel.jeelpa.musicplayer.common.clients.AlbumClient
import tel.jeelpa.musicplayer.common.clients.ArtistClient
import tel.jeelpa.musicplayer.common.clients.HomeFeedClient
import tel.jeelpa.musicplayer.common.clients.TrackClient

interface ClientsHolder {
    fun getName(): String
    fun getHomeFeedClient(): HomeFeedClient
    fun getAlbumClient(): AlbumClient
    fun getArtistClient(): ArtistClient
    fun getTrackClient(): TrackClient
}