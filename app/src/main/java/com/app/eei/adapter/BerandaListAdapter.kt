package com.app.eei.adapter

import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.eei.databinding.ItemCardBinding
import com.app.eei.databinding.ItemCardOneBinding
import com.app.eei.entity.News
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class BerandaListAdapter(private val List: ArrayList<News>): RecyclerView.Adapter<BerandaListAdapter.ListViewHolder>() {

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
    fun clear() {
        List.clear()
        notifyDataSetChanged()
    }
    fun addAll(tweetList: List<News>) {
        List.addAll(tweetList)
        notifyDataSetChanged()
    }
    inner class ListViewHolder(private val binding: ItemCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(news: News){
            with(binding){
                imgNews?.let {
                    Glide.with(itemView.context)
                        .load(news.imgNews)
                        .into(it)
                }
                titleNews.text=news.title
                dateNews.text=news.dateNews
                contentNews.text= Html.fromHtml(news.contentNews)
                itemView.setOnClickListener{
                    onItemClickCallback?.onItemClicked(news)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding=ItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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