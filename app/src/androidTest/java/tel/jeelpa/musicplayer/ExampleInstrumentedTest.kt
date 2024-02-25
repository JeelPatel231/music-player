package tel.jeelpa.musicplayer

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.GrantPermissionRule
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import tel.jeelpa.musicplayer.localplugin.LocalPluginClientsHolder
import tel.jeelpa.musicplayer.localplugin.content.LocalPluginContentResolver

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @get:Rule
    val permissionRule: GrantPermissionRule = GrantPermissionRule.grant(
        android.Manifest.permission.READ_MEDIA_AUDIO
    )


    @Test
    fun useAppContext(): Unit = runBlocking {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("tel.jeelpa.musicplayer", appContext.packageName)

        val localResolver = LocalPluginContentResolver(appContext.contentResolver)
        val localPlugin = LocalPluginClientsHolder(localResolver)
//        localPlugin.getHomeFeedClient()
//            .getAlbums(0,0)
//            .first()
//            .also { it.getName().dbg("Album Name") }
//            .getAlbumArtists()
//            .map { it.getName() }
//            .onEach { it.dbg() }


//        localPlugin.getHomeFeedClient()
//            .getSongs(0,0)
//            .dbg("Songs")
//            .ifEmpty { error("List was empty, did you forget to grant permissions or you actually don't have any songs on device") }
//            .map { it.getAlbum() }
//            .dbg("Albums")
//            .map { it.getAlbumArtists() }
//            .flatten().onEach { it.getName().dbg("ARITST NAMES") }
//            .map { it.getSongs(0,0) }
//            .flatten()
//            .onEach { it.getName().dbg("Song by artist NAMES") }
//            .map { it.getArtists().dbg("Artists") }
//            .flatten().onEach { it.getName().dbg("Artist of song NAMES") }
//            .map { it.getAlbums(0,0) }
//            .flatten().onEach { it.getName().dbg("Albums By Artist NAMES") }

    }
}
