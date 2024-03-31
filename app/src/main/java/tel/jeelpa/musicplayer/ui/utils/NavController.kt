package tel.jeelpa.musicplayer.ui.utils

import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import tel.jeelpa.musicplayer.R


fun FragmentActivity.getNavController(): NavController {
    val navHostFragment = supportFragmentManager.findFragmentById(R.id.main_activity_container) as NavHostFragment
    return navHostFragment.navController
}
