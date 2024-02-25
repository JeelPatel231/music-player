package tel.jeelpa.musicplayer.localplugin.clients

import tel.jeelpa.musicplayer.common.clients.AlbumClient
import tel.jeelpa.musicplayer.common.models.Album
import tel.jeelpa.musicplayer.localplugin.content.AlbumResolver

class LocalAlbumClient(
    private val albumResolver: AlbumResolver
): AlbumClient {
    override fun search(query: String, page: Int, perPage: Int): List<Album> {
        if(page != 0 || perPage != 0) TODO()
        return albumResolver.search(query)
    }
}