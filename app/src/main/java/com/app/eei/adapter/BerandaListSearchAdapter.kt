package com.app.eei.adapter

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.app.eei.databinding.ItemCardBigBinding

import com.app.eei.entity.News
import com.app.eei.ui.admin.detail.AdminDetailActivity
import com.bumptech.glide.Glide


class BerandaListSearchAdapter: RecyclerView.Adapter<BerandaListSearchAdapter.ListViewHolder>() {
    private var onItemClickCallback: OnItemClickCallback? = null
    private val List= arrayListOf<News>()


    fun setOnItemClickCallback(onItemClickCallback: BerandaListSearchAdapter.OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
    fun clear() {
        List.clear()
        notifyDataSetChanged()
        notifyItemInserted(List.size)
    }
    fun addAll(tweetList: List<News>) {
        List.addAll(tweetList)
        notifyDataSetChanged()
        notifyItemInserted(List.size)
    }
    fun sizeData(): Int {
        return List.size
    }
    inner class ListViewHolder(private val binding: ItemCardBigBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(news: News){
            with(binding){
                imgNews?.let {
                    Glide.with(itemView.context)
                        .load(news.imgNews)
                        .into(it)
                }
                val bundle=Bundle()
                titleNews.text=news.title
                dateNews.text=news.dateNews
                binding.card.setOnClickListener {
                    val intent= Intent(itemView.context, AdminDetailActivity::class.java)
                    intent.putExtra(AdminDetailActivity.EXTRA_DATA,news)
                    startActivity(itemView.context,intent,bundle)
                }
//                contentNews.text= Html.fromHtml(news.contentNews)
                itemView.setOnClickListener{
                    onItemClickCallback?.onItemClicked(news)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding= ItemCardBigBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(List[position])
    }

    override fun getItemCount(): Int {
        return List.size

    }
    interface OnItemClickCallback {
        fun onItemClicked(data: News)
    }
}