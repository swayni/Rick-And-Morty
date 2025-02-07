package sw.swayni.basemvvm.mvvm.adapter

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.viewbinding.ViewBinding

abstract class BaseViewHolder<T> (binding: ViewBinding) : ViewHolder(binding.root) {

    abstract fun bindData(data : T)
}