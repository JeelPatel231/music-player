package tel.jeelpa.musicplayer.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

//abstract class GenericRecyclerAdapter<TData, TBindingType : ViewBinding>(
//    private val inflateCallback: (LayoutInflater, ViewGroup?, Boolean) -> TBindingType,
//    initialData: Collection<TData> = emptyList(),
//) : RecyclerView.Adapter<GenericRecyclerAdapter.ViewHolder<TData, TBindingType>>() {
//
//    private var _data: MutableList<TData> = initialData.toMutableList()
//
//    val data: List<TData>
//        get() = _data
//
//    fun addAll(newData: Collection<TData>) {
//        val oldDataSize = data.size
//        _data.addAll(newData)
//        notifyItemRangeInserted(oldDataSize, newData.size)
//    }
//
//    fun add(newData: TData) {
//        _data.add(newData)
//        notifyItemRangeInserted(data.size - 1, data.size)
//    }
//
//    fun setData(newData: Collection<TData>) {
//        val oldSize = data.size
//        _data = newData.toMutableList()
//        notifyItemRangeRemoved(0, oldSize)
//        notifyItemRangeInserted(0, data.size)
//    }
//
//    override fun getItemCount() = data.size
//
//
//    class ViewHolder<TData, TBindingType : ViewBinding>(
//        private val binding: TBindingType,
//        private val callback: (TBindingType, TData, Int) -> Unit
//    ) : RecyclerView.ViewHolder(binding.root) {
//
//        fun bind(entry: TData, position: Int) {
//            callback(binding, entry, position)
//        }
//    }
//
//    abstract fun onBind(binding: TBindingType, entry: TData, position: Int)
//    override fun onCreateViewHolder(
//        viewGroup: ViewGroup,
//        viewType: Int
//    ): ViewHolder<TData, TBindingType> {
//        val binding = inflateCallback(LayoutInflater.from(viewGroup.context), viewGroup, false)
//        return ViewHolder(binding, this::onBind)
//    }
//
//    override fun onBindViewHolder(viewHolder: ViewHolder<TData, TBindingType>, position: Int) {
//        viewHolder.bind(data[position], position)
//    }
//}

class DataClassDiffCallback<TData : Any> : DiffUtil.ItemCallback<TData>() {
    override fun areItemsTheSame(oldItem: TData, newItem: TData): Boolean {
        return oldItem == newItem
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: TData, newItem: TData): Boolean {
        return oldItem == newItem
    }
}

abstract class GenericListAdapter<TData : Any, TBindingType : ViewBinding>(
    diffCallback:  DiffUtil.ItemCallback<TData> = DataClassDiffCallback()
): ListAdapter<TData, GenericListAdapter.ViewHolder<TData, TBindingType>>(diffCallback) {
    class ViewHolder<TData, TBindingType : ViewBinding>(
        private val binding: TBindingType,
        private val callback: (TBindingType, TData, Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(entry: TData, position: Int) {
            callback(binding, entry, position)
        }
    }

    abstract fun inflateCallback(
        layoutInflator: LayoutInflater,
        viewGroup: ViewGroup?,
        attachToParent: Boolean
    ): TBindingType

    abstract fun onBind(binding: TBindingType, entry: TData, position: Int)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder<TData, TBindingType> {
        val binding = inflateCallback(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, this::onBind)
    }

    override fun onBindViewHolder(holder: ViewHolder<TData, TBindingType>, position: Int) {
        holder.bind(currentList[position], position)
    }

}
