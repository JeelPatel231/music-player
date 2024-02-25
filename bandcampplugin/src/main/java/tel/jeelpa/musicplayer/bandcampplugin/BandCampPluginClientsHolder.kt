package tel.jeelpa.musicplayer.bandcampplugin

import org.schabi.newpipe.extractor.NewPipe
import org.schabi.newpipe.extractor.ServiceList.Bandcamp
import tel.jeelpa.musicplayer.common.ClientsHolder
import tel.jeelpa.musicplayer.common.models.Album
import tel.jeelpa.musicplayer.common.models.Artist
import tel.jeelpa.musicplayer.common.clients.HomeFeedClient
import tel.jeelpa.musicplayer.common.models.Track

class BandCampPluginClientsHolder : ClientsHolder {
    init {
        NewPipe.init(CustomDownloader.getInstance())
    }

    override fun getName(): String =
        "BandCamp"

    override fun getHomeFeedClient(): HomeFeedClient =
        Bandcamp.getHomeFeedClient()

    override fun getAlbumClient(id: String): Album =
        Bandcamp.getAlbumClient(id)

    override fun getArtistClient(id: String): Artist =
        Bandcamp.getArtistClient(id)

    override fun getTrackClient(id: String): Track =
        Bandcamp.getTrackClient(id)

}
