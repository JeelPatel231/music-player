package tel.jeelpa.musicplayer.soundcloudplugin.clients

import tel.jeelpa.musicplayer.common.clients.AlbumClient
import tel.jeelpa.musicplayer.common.models.Album

class SCAlbumClient: AlbumClient {
    override fun search(query: String, page: Int, perPage: Int): List<Album> {
        TODO("Not yet implemented")
    }
}