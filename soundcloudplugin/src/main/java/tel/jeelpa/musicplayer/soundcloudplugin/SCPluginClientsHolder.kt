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

    override fun getAlbumClient(): AlbumClient {
        TODO("Not yet implemented")
    }

    override fun getArtistClient(): ArtistClient {
        TODO("Not yet implemented")
    }

    override fun getTrackClient(): TrackClient {
        TODO("Not yet implemented")
    }

//    override fun getAlbumClient(id: String): Album =
//        SoundCloud.getAlbumClient(id)
//
//    override fun getArtistClient(id: String): Artist =
//        SoundCloud.getArtistClient(id)
//
//    override fun getTrackClient(id: String): Track =
//        SoundCloud.getTrackClient(id)

}
