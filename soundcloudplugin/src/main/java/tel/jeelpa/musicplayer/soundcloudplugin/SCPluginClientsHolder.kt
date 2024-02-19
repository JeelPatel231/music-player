package tel.jeelpa.musicplayer.soundcloudplugin

import org.schabi.newpipe.extractor.NewPipe
import org.schabi.newpipe.extractor.ServiceList.SoundCloud
import tel.jeelpa.musicplayer.common.ClientsHolder
import tel.jeelpa.musicplayer.common.clients.AlbumClient
import tel.jeelpa.musicplayer.common.clients.ArtistClient
import tel.jeelpa.musicplayer.common.clients.HomeFeedClient
import tel.jeelpa.musicplayer.common.clients.TrackClient

class SCPluginClientsHolder : ClientsHolder {
    init {
        NewPipe.init(CustomDownloader.getInstance())
    }

    override fun getName(): String =
        "SoundCloud"

    override fun getHomeFeedClient(): HomeFeedClient =
        SoundCloud.getHomeFeedClient()

    override fun getAlbumClient(id: String): AlbumClient =
        SoundCloud.getAlbumClient(id)

    override fun getArtistClient(id: String): ArtistClient =
        SoundCloud.getArtistClient(id)

    override fun getTrackClient(id: String): TrackClient =
        SoundCloud.getTrackClient(id)

}


class Test {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val a = SCPluginClientsHolder()

            val album = a.getAlbumClient("https://soundcloud.com/majorlazer/sets/peace-is-the-mission-extended")
            println( album.getName() )
            album.getSongs(0,0)
                .onEach(::println)
                .map { it.getMediaSource() }
                .forEach(::println)
            println( album.getAlbumArt() )
        }
    }
}