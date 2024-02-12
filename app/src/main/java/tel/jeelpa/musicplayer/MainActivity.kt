package tel.jeelpa.musicplayer

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import tel.jeelpa.musicplayer.databinding.ActivityMainBinding
import tel.jeelpa.musicplayer.player.AppPlayer
import tel.jeelpa.musicplayer.player.models.Song
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var player: AppPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.debugBtn.setOnClickListener {
            val song =
                Song.HttpUrl("https://file-examples.com/storage/fe63e96e0365c0e1e99a842/2017/11/file_example_MP3_5MG.mp3")
            player.setMediaItem(song)
            player.play()
        }
    }

}

fun Context.showToast(data: String, time: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, data, time).show()
}
