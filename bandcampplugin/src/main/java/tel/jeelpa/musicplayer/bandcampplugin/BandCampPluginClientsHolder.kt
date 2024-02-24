package tel.jeelpa.musicplayer.bandcampplugin

import org.schabi.newpipe.extractor.NewPipe
import org.schabi.newpipe.extractor.ServiceList.Bandcamp
import tel.jeelpa.musicplayer.common.ClientsHolder
import tel.jeelpa.musicplayer.common.clients.AlbumClient
import tel.jeelpa.musicplayer.common.clients.ArtistClient
import tel.jeelpa.musicplayer.common.clients.HomeFeedClient
import tel.jeelpa.musicplayer.common.clients.TrackClient

class BandCampPluginClientsHolder : ClientsHolder {
    init {
        NewPipe.init(CustomDownloader.getInstance())
    }

    override fun getName(): String =
        "BandCamp"

    override fun getHomeFeedClient(): HomeFeedClient =
        Bandcamp.getHomeFeedClient()

    override fun getAlbumClient(id: String): AlbumClient =
        Bandcamp.getAlbumClient(id)

    override fun getArtistClient(id: String): ArtistClient =
        Bandcamp.getArtistClient(id)

    override fun getTrackClient(id: String): TrackClient =
        Bandcamp.getTrackClient(id)

}
