package tel.jeelpa.musicplayer.localplugin

import tel.jeelpa.musicplayer.common.ClientsHolder
import tel.jeelpa.musicplayer.common.clients.AlbumClient
import tel.jeelpa.musicplayer.common.clients.ArtistClient
import tel.jeelpa.musicplayer.common.clients.HomeFeedClient
import tel.jeelpa.musicplayer.common.clients.TrackClient
import tel.jeelpa.musicplayer.localplugin.content.LocalPluginContentResolver

class LocalPluginClientsHolder(
    private val resolver: LocalPluginContentResolver
): ClientsHolder {

    override fun getName(): String =
        "tel.jeelpa.musicplayer.offline"

    override fun getHomeFeedClient(): HomeFeedClient =
        LocalPluginHomeFeedClient(resolver)

    override fun getAlbumClient(id: String): AlbumClient =
        resolver.albumResolver.getById(id)

    override fun getArtistClient(id: String): ArtistClient =
        resolver.artistResolver.getById(id)

    override fun getTrackClient(id: String): TrackClient =
        resolver.trackResolver.getById(id)
}