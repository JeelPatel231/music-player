package tel.jeelpa.musicplayer.ytmplugin

import org.schabi.newpipe.extractor.NewPipe
import org.schabi.newpipe.extractor.ServiceList.YouTube
import tel.jeelpa.musicplayer.common.ClientsHolder
import tel.jeelpa.musicplayer.common.models.Album
import tel.jeelpa.musicplayer.common.models.Artist
import tel.jeelpa.musicplayer.common.clients.HomeFeedClient
import tel.jeelpa.musicplayer.common.models.Track

class YTMPluginClientsHolder : ClientsHolder {
    init {
        NewPipe.init(CustomDownloader.getInstance())
    }

    override fun getName(): String =
        "Youtube"

    override fun getHomeFeedClient(): HomeFeedClient =
        YouTube.getHomeFeedClient()

    override fun getAlbumClient(id: String): Album =
        YouTube.getAlbumClient(id)

    override fun getArtistClient(id: String): Artist =
        YouTube.getArtistClient(id)

    override fun getTrackClient(id: String): Track =
        YouTube.getTrackClient(id)

}


//class Test {
//    companion object {
//        @JvmStatic
//        fun main(args: Array<String>) {
//            val a = YTMPluginClientsHolder()
//
//            val album = a.getAlbumClient("https://music.youtube.com/playlist?list=OLAK5uy_l4UqNJCpAF3kNaV37LHdRc_A07MmVdiSU")
//            album.getSongs(0,0).onEach {
//                println( (it.getMediaSource().first() as StringMediaSource).url)
//                println(it.getName())
//                println(it.getCover())
//                println("---")
//            }
//        }
//    }
//}