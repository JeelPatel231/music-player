package tel.jeelpa.musicplayer.localplugin

import tel.jeelpa.musicplayer.common.ClientsHolder
import tel.jeelpa.musicplayer.common.clients.AlbumClient
import tel.jeelpa.musicplayer.common.clients.ArtistClient
import tel.jeelpa.musicplayer.common.clients.HomeFeedClient
import tel.jeelpa.musicplayer.common.clients.TrackClient
import tel.jeelpa.musicplayer.localplugin.clients.LocalAlbumClient
import tel.jeelpa.musicplayer.localplugin.clients.LocalArtistClient
import tel.jeelpa.musicplayer.localplugin.clients.LocalTrackClient
import tel.jeelpa.musicplayer.localplugin.content.LocalPluginContentResolver

class LocalPluginClientsHolder(
    private val resolver: LocalPluginContentResolver
): ClientsHolder {

    override fun getName(): String =
        "tel.jeelpa.musicplayer.offline"

    override fun getHomeFeedClient(): HomeFeedClient =
        LocalPluginHomeFeedClient(resolver)

    override fun getAlbumClient(): AlbumClient =
        LocalAlbumClient(resolver.albumResolver)

    override fun getArtistClient(): ArtistClient =
        LocalArtistClient(resolver.artistResolver)

    override fun getTrackClient(): TrackClient =
        LocalTrackClient(resolver.trackResolver)
}