package com.app.eei.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.eei.databinding.ItemSettingBinding
import com.app.eei.entity.Menu
import com.bumptech.glide.Glide

class SettingsMenuAdapter(private val listMenu: ArrayList<Menu>): RecyclerView.Adapter<SettingsMenuAdapter.ListViewHolder>() {
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
    inner class ListViewHolder (private val binding: ItemSettingBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(menu: Menu){
            with(binding){
                Glide.with(itemView.context)
                    .load(menu.ic)
                    .into(icLogo)
                titleMenu.text = menu.title
                itemView.setOnClickListener{
                    onItemClickCallback?.onItemClicked(menu)
                }
            }
        }
    }
    interface OnItemClickCallback {
        fun onItemClicked(data: Menu)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding=ItemSettingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listMenu[position])
    }

    override fun getItemCount(): Int {
        return listMenu.size
    }
}