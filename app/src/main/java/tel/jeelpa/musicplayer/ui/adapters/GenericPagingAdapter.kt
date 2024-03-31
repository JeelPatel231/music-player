package tel.jeelpa.musicplayer.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding


class GenericViewHolder<TData, TBindingType : ViewBinding>(
    private val binding: TBindingType,
    private val callback: (TBindingType, TData, Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(entry: TData, position: Int) {
        callback(binding, entry, position)
    }
}

abstract class GenericPagingAdapter<TData : Any, TBindingType : ViewBinding>(
    diffCallback: DiffUtil.ItemCallback<TData> = DataClassDiffCallback()
) : PagingDataAdapter<TData, GenericViewHolder<TData, TBindingType>>(diffCallback) {

    abstract fun inflateCallback(
        layoutInflator: LayoutInflater,
        viewGroup: ViewGroup?,
        attachToParent: Boolean
    ): TBindingType

    abstract fun onBind(binding: TBindingType, entry: TData, position: Int)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GenericViewHolder<TData, TBindingType> {
        val binding = inflateCallback(LayoutInflater.from(parent.context), parent, false)
        return GenericViewHolder(binding, this::onBind)
    }

    override fun onBindViewHolder(
        holder: GenericViewHolder<TData, TBindingType>,
        position: Int
    ) {
        val currItem = getItem(position) ?: return
        holder.bind(currItem, position)
    }

}
