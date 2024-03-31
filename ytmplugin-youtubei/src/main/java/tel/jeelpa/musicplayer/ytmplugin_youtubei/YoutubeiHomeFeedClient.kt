package tel.jeelpa.musicplayer.ytmplugin_youtubei

import androidx.paging.PagingData
import androidx.paging.PagingSource
import dev.toastbits.ytmkt.endpoint.SongFeedLoadResult
import dev.toastbits.ytmkt.impl.youtubei.YoutubeiApi
import dev.toastbits.ytmkt.model.external.ThumbnailProvider
import dev.toastbits.ytmkt.model.external.mediaitem.YtmArtist
import dev.toastbits.ytmkt.model.external.mediaitem.YtmPlaylist
import dev.toastbits.ytmkt.model.external.mediaitem.YtmSong
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import tel.jeelpa.musicplayer.common.clients.AlbumFeedEntry
import tel.jeelpa.musicplayer.common.clients.ArtistFeedEntry
import tel.jeelpa.musicplayer.common.clients.HomeFeedClient
import tel.jeelpa.musicplayer.common.clients.HomeFeedEntry
import tel.jeelpa.musicplayer.common.clients.TrackFeedEntry

class YoutubeiHomeFeedClient(
    private val apiClient: YoutubeiApi
) : HomeFeedClient {
    private var feed: SongFeedLoadResult? = null

    override fun getHomeFeed(): Flow<PagingData<HomeFeedEntry>> = continuationFlow<String, HomeFeedEntry> {
        val result = feed?.also {
            feed = null
        } ?: apiClient.SongFeed.getSongFeed(
            continuation = it
        ).getOrThrow()

        val data = result.layouts.map { itemLayout ->
            when (itemLayout.items.first()) {
                is YtmSong -> TrackFeedEntry(
                    itemLayout.title!!.getString("en-US"),
                    // TODO: REAL PAGINATION
                    flowOf((itemLayout.items as List<YtmSong>).map { song ->
                        apiClient.getTrack(
                            song.id,
                            song.name!!,
                            song.thumbnail_provider!!.getThumbnailUrl(ThumbnailProvider.Quality.LOW)!!
                        )
                    }.toPagedData())
                )


                is YtmPlaylist -> AlbumFeedEntry(
                    itemLayout.title!!.getString("en-US"),
                    // TODO: REAL PAGINATION
                    flowOf((itemLayout.items as List<YtmPlaylist>).map { playlist ->
                        apiClient.getAlbum(
                            playlist.id,
                            playlist.name!!,
                            apiClient.getArtist(
                                playlist.artist?.id ?: playlist.owner_id ?: "Unknown Playlist Author",
                                playlist.artist?.name ?: "Unknwon Playlist Author",
                                playlist.artist?.thumbnail_provider?.getThumbnailUrl(
                                    ThumbnailProvider.Quality.LOW
                                )
                            ),
                            playlist.thumbnail_provider?.getThumbnailUrl(ThumbnailProvider.Quality.LOW) ?: "",
                        )
                    }.toPagedData())
                )

                is YtmArtist -> ArtistFeedEntry(
                    itemLayout.title!!.getString("en-US"),
                    // TODO: REAL PAGINATION
                    flowOf((itemLayout.items as List<YtmArtist>).map { artist ->
                        apiClient.getArtist(
                            artist.id,
                            artist.name!!,
                            artist.thumbnail_provider!!.getThumbnailUrl(ThumbnailProvider.Quality.LOW)!!,
                        )
                    }.toPagedData())
                )

                else -> error("Unreachable")
            }
        }

        PagingSource.LoadResult.Page(
            data,
            null,
            result.ctoken
        )
    }
}
