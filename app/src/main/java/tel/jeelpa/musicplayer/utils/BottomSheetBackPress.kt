package tel.jeelpa.musicplayer.utils

import android.view.View
import androidx.activity.BackEventCompat
import androidx.activity.OnBackPressedCallback
import com.google.android.material.bottomsheet.BottomSheetBehavior

object BottomSheetBackPress {

    private fun <T : View> predictiveBackGestureForBottomSheet(bottomSheetBehavior: BottomSheetBehavior<T>): OnBackPressedCallback {
        return object : OnBackPressedCallback(/* enabled= */false) {
            override fun handleOnBackStarted(backEvent: BackEventCompat) {
                bottomSheetBehavior.startBackProgress(backEvent)
            }

            override fun handleOnBackProgressed(backEvent: BackEventCompat) {
                bottomSheetBehavior.updateBackProgress(backEvent)
            }

            override fun handleOnBackPressed() {
                bottomSheetBehavior.handleBackInvoked()
            }

            override fun handleOnBackCancelled() {
                bottomSheetBehavior.cancelBackProgress()
            }
        }
    }


    fun <T : View> getBackPressedHandler(
        bottomSheetBehavior: BottomSheetBehavior<T>,
        hideableState: Boolean,
        onExpanded: () -> Unit = {},
        onCollapsed: () -> Unit = {},
    ): OnBackPressedCallback {
        val bottomSheetBackCallback = predictiveBackGestureForBottomSheet(bottomSheetBehavior)

        bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_EXPANDED, BottomSheetBehavior.STATE_HALF_EXPANDED -> {
                        bottomSheetBehavior.isHideable = false
                        bottomSheetBackCallback.isEnabled = true
                        onExpanded()
                    }

                    BottomSheetBehavior.STATE_COLLAPSED, BottomSheetBehavior.STATE_HIDDEN -> {
                        bottomSheetBehavior.isHideable = hideableState
                        bottomSheetBackCallback.isEnabled = false
                        onCollapsed()
                    }

                    // else Do nothing, only change callback enabled for "stable" states.
                    else -> Unit
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {}
        })

        return bottomSheetBackCallback
    }
}