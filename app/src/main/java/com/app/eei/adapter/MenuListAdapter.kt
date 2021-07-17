package com.app.eei.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.eei.databinding.ItemMenuBinding
import com.app.eei.entity.Menu
import com.bumptech.glide.Glide

class MenuListAdapter(private val List: ArrayList<Menu>): RecyclerView.Adapter<MenuListAdapter.ListViewHolder>() {
    fun clear() {
        List.clear()
        notifyDataSetChanged()
    }
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
    inner class ListViewHolder(private val binding: ItemMenuBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(menu: Menu){
            with(binding){
                icMenu?.let {
                    Glide.with(itemView.context)
                        .load(menu.ic)
                        .into(it)
                }
                titleMenu.text=menu.title
                itemView.setOnClickListener{
                    onItemClickCallback?.onItemClicked(menu)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding=ItemMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(List[position])
    }

    override fun getItemCount(): Int {
        return 6
    }
    interface OnItemClickCallback {
        fun onItemClicked(data: Menu)
    }
}