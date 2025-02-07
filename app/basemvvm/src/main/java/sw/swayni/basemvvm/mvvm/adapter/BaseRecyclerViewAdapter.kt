package sw.swayni.basemvvm.mvvm.adapter

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView

import sw.swayni.basemvvm.R

abstract class BaseRecyclerViewAdapter<T> : RecyclerView.Adapter<BaseViewHolder<T>>(){

    val list = ArrayList<T>()
    var itemClickListener: ((T) -> Unit)? = null

    abstract fun getCreateViewHolder(viewGroup: ViewGroup, viewType: Int): BaseViewHolder<T>

    fun addList(list: List<T>){
        this.list.addAll(list)
        notifyItemInserted(list.size)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list: List<T>){
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        holder.bindData(list[position])
        holder.itemView.animation = AnimationUtils.loadAnimation(holder.itemView.context, R.anim.left_to_right)
        holder.itemView.setOnClickListener {
            itemClickListener?.invoke(list[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T> = getCreateViewHolder(viewGroup = parent, viewType = viewType)


}