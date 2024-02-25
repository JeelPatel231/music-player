package tel.jeelpa.musicplayer.localplugin.clients

import tel.jeelpa.musicplayer.common.clients.ArtistClient
import tel.jeelpa.musicplayer.common.models.Artist
import tel.jeelpa.musicplayer.localplugin.content.ArtistResolver

class LocalArtistClient(
    private val artistResolver: ArtistResolver
): ArtistClient {
    override fun search(query: String, page: Int, perPage: Int): List<Artist> {
        if(page != 0 || perPage != 0) TODO()
        return artistResolver.search(query)
    }
}