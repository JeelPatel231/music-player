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


class LifecycleViewHolder<TData, TBindingType : ViewBinding>(
    private val binding: TBindingType,
    private val callback: (TBindingType, TData, Int, Lifecycle) -> Unit
) : RecyclerView.ViewHolder(binding.root), LifecycleOwner {

    private val lifecycleRegistry: LifecycleRegistry = LifecycleRegistry(this)

    fun bind(entry: TData, position: Int) {
        callback(binding, entry, position, lifecycle)
    }

    init {
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
    }

    override val lifecycle: Lifecycle
        get() = lifecycleRegistry

    fun onAttachViewHolder() =
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START)

    fun onDetachViewHolder() =
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE)

    fun onRecycled() =
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
}

abstract class LifecyclePagingAdapter<TData : Any, TBindingType : ViewBinding>(
    diffCallback: DiffUtil.ItemCallback<TData> = DataClassDiffCallback()
) : PagingDataAdapter<TData, LifecycleViewHolder<TData, TBindingType>>(diffCallback) {

    abstract fun inflateCallback(
        layoutInflator: LayoutInflater,
        viewGroup: ViewGroup?,
        attachToParent: Boolean
    ): TBindingType

    abstract fun onBind(binding: TBindingType, entry: TData, position: Int, lifecycle: Lifecycle)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LifecycleViewHolder<TData, TBindingType> {
        val binding = inflateCallback(LayoutInflater.from(parent.context), parent, false)
        return LifecycleViewHolder(binding, this::onBind)
    }

    override fun onBindViewHolder(
        holder: LifecycleViewHolder<TData, TBindingType>,
        position: Int
    ) {
        val currItem = getItem(position) ?: return
        holder.bind(currItem, position)
    }


    /// LIFECYCLE HANDLING METHODS
    override fun onViewRecycled(holder: LifecycleViewHolder<TData, TBindingType>) {
        super.onViewRecycled(holder)
        holder.onRecycled()
    }

    override fun onViewAttachedToWindow(holder: LifecycleViewHolder<TData, TBindingType>) {
        super.onViewAttachedToWindow(holder)
        holder.onAttachViewHolder()
    }

    override fun onViewDetachedFromWindow(holder: LifecycleViewHolder<TData, TBindingType>) {
        super.onViewDetachedFromWindow(holder)
        holder.onDetachViewHolder()
    }
}
