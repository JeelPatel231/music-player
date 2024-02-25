package tel.jeelpa.musicplayer.localplugin

import tel.jeelpa.musicplayer.common.clients.HomeFeedClient
import tel.jeelpa.musicplayer.common.models.Album
import tel.jeelpa.musicplayer.common.models.Artist
import tel.jeelpa.musicplayer.common.models.Track
import tel.jeelpa.musicplayer.localplugin.content.LocalPluginContentResolver

class LocalPluginHomeFeedClient(
    private val resolver: LocalPluginContentResolver
): HomeFeedClient {
    override suspend fun getSongs(offset: Int, limit: Int): List<Track> {
        // TODO: pagination
        return resolver.trackResolver.getAll()
    }

    override suspend fun getAlbums(offset: Int, limit: Int): List<Album> {
        // TODO: pagination
        return resolver.albumResolver.getAll()
    }

    override suspend fun getArtists(count: Int, limit: Int): List<Artist> {
        // TODO: pagination
        return resolver.artistResolver.getAll()
    }
}