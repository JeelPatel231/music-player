package tel.jeelpa.musicplayer.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Used in UI to display data
 * */

interface ItemSmallData {
    val id: String
    val title: String
    val avatar: String?
}

@Parcelize
data class ItemSmallDataClass(
    override val id: String,
    override val title: String,
    override val avatar: String?
) : ItemSmallData, Parcelable

fun ItemSmallData.toDataClass() = ItemSmallDataClass(id, title, avatar)