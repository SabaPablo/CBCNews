package com.doce.cactus.saba.feq.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.doce.cactus.saba.feq.models.News
import com.doce.cactus.saba.feq.R
import com.doce.cactus.saba.feq.databinding.NewsItemBinding
import java.text.SimpleDateFormat
import java.util.*

class NewsAdapter: ListAdapter<News, NewsAdapter.ViewHolder>(MyDiffCallback()){
    class ViewHolder private constructor( val binding: NewsItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: News) {

            Glide.with(binding.root)
                .load(item.images.square140)
                .error(AppCompatResources.getDrawable(binding.imageNewsIv.context, R.drawable.no_image))

                .into(binding.imageNewsIv)
            binding.titleTv.text = item.title
            binding.publishedTv.text = "Posted: ${item.readablePublishedAt}"

        }


        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = NewsItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class MyDiffCallback : DiffUtil.ItemCallback<News>() {
        override fun areItemsTheSame(firstItem: News, secondItem: News) =
            firstItem.id == secondItem.id

        override fun areContentsTheSame(firstItem: News, secondItem: News) =
            firstItem == secondItem
    }
}