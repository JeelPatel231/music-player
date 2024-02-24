package tel.jeelpa.musicplayer.localplugin

import tel.jeelpa.musicplayer.common.clients.AlbumClient
import tel.jeelpa.musicplayer.common.clients.ArtistClient
import tel.jeelpa.musicplayer.common.clients.HomeFeedClient
import tel.jeelpa.musicplayer.common.clients.TrackClient
import tel.jeelpa.musicplayer.localplugin.content.LocalPluginContentResolver

class LocalPluginHomeFeedClient(
    private val resolver: LocalPluginContentResolver
): HomeFeedClient {
    override suspend fun getSongs(offset: Int, limit: Int): List<TrackClient> {
        // TODO: pagination
        return resolver.trackResolver.getAll()
    }

    override suspend fun getAlbums(offset: Int, limit: Int): List<AlbumClient> {
        // TODO: pagination
        return resolver.albumResolver.getAll()
    }

    override suspend fun getArtists(count: Int, limit: Int): List<ArtistClient> {
        // TODO: pagination
        return resolver.artistResolver.getAll()
    }
}