package tel.jeelpa.musicplayer.soundcloudplugin

import org.schabi.newpipe.extractor.NewPipe
import org.schabi.newpipe.extractor.ServiceList
import org.schabi.newpipe.extractor.StreamingService
import tel.jeelpa.musicplayer.common.ClientsHolder
import tel.jeelpa.musicplayer.common.clients.AlbumClient
import tel.jeelpa.musicplayer.common.clients.ArtistClient
import tel.jeelpa.musicplayer.common.clients.HomeFeedClient
import tel.jeelpa.musicplayer.common.clients.TrackClient
import tel.jeelpa.musicplayer.soundcloudplugin.clients.NPAlbumClient
import tel.jeelpa.musicplayer.soundcloudplugin.clients.NPArtistClient
import tel.jeelpa.musicplayer.soundcloudplugin.clients.NPTrackClient

abstract class NewPipePluginClientsHolder(
    private val service: StreamingService
): ClientsHolder {
    init {
        NewPipe.init(CustomDownloader.getInstance())
    }

    override fun getHomeFeedClient(): HomeFeedClient =
        service.getHomeFeedClient()

    override fun getAlbumClient(): AlbumClient =
        NPAlbumClient(service)

    override fun getArtistClient(): ArtistClient =
        NPArtistClient(service)

    override fun getTrackClient(): TrackClient =
        NPTrackClient(service)

}

// TODO: REMOVE
val SoundCloudPlugin = object : NewPipePluginClientsHolder(ServiceList.SoundCloud) {
    override fun getName(): String = "SoundCloud"
}

val BandCampPlugin = object : NewPipePluginClientsHolder(ServiceList.Bandcamp) {
    override fun getName(): String = "BandCamp"
}

val YoutubePlugin = object : NewPipePluginClientsHolder(ServiceList.YouTube) {
    override fun getName(): String = "Youtube"
}

val MediaCCCPlugin = object : NewPipePluginClientsHolder(ServiceList.MediaCCC) {
    override fun getName(): String = "MediaCCC"
}

val PeertubePlugin = object : NewPipePluginClientsHolder(ServiceList.PeerTube) {
    override fun getName(): String = "PeerTube"
}
