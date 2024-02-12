package tel.jeelpa.musicplayer.ytmplugin

import org.schabi.newpipe.extractor.NewPipe
import org.schabi.newpipe.extractor.ServiceList.YouTube
import tel.jeelpa.musicplayer.common.ClientsHolder
import tel.jeelpa.musicplayer.common.clients.AlbumClient
import tel.jeelpa.musicplayer.common.clients.ArtistClient
import tel.jeelpa.musicplayer.common.clients.HomeFeedClient
import tel.jeelpa.musicplayer.common.clients.TrackClient

class YTMPluginClientsHolder : ClientsHolder {
    init {
        NewPipe.init(CustomDownloader.getInstance())
    }

    override fun getHomeFeedClient(): HomeFeedClient =
        YouTube.getHomeFeedClient()

    override fun getAlbumClient(id: String): AlbumClient =
        YouTube.getAlbumClient(id)

    override fun getArtistClient(id: String): ArtistClient =
        YouTube.getArtistClient(id)

    override fun getTrackClient(id: String): TrackClient =
        YouTube.getTrackClient(id)

}


class Test {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val a = YTMPluginClientsHolder()

            val album = a.getAlbumClient("https://music.youtube.com/playlist?list=OLAK5uy_l4UqNJCpAF3kNaV37LHdRc_A07MmVdiSU")
            println( album.getName() )
            album.getSongs(0,0).forEach(::println)
            println( album.getAlbumArt() )
        }
    }
}