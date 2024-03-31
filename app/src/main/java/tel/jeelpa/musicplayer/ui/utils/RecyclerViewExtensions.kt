package tel.jeelpa.musicplayer.ui.utils

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView


fun <ViewHolder : RecyclerView.ViewHolder> RecyclerView.setupHorizontalWithAdapter(
    adapter: RecyclerView.Adapter<ViewHolder>,
){
    layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    setAdapter(adapter)
}

fun <ViewHolder : RecyclerView.ViewHolder> RecyclerView.setupVerticalWithAdapter(
    adapter: RecyclerView.Adapter<ViewHolder>,
){
    layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    setAdapter(adapter)
}

fun <ViewHolder : RecyclerView.ViewHolder> RecyclerView.setupHorizontalGridWithAdapter(
    adapter: RecyclerView.Adapter<ViewHolder>,
    spanCount: Int = 4,
){
    val snapper = LinearSnapHelper()
    snapper.attachToRecyclerView(this)
    layoutManager = GridLayoutManager(context, spanCount, LinearLayoutManager.HORIZONTAL, false)
    setAdapter(adapter)
}
