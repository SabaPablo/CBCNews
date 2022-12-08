package com.doce.cactus.saba.cbcnews.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.doce.cactus.saba.cbcnews.R
import com.doce.cactus.saba.cbcnews.databinding.NewsItemBinding
import com.doce.cactus.saba.cbcnews.models.News

class NewsAdapter: ListAdapter<News, NewsAdapter.ViewHolder>(MyDiffCallback()){
    class ViewHolder private constructor(private val binding: NewsItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: News) {

            Glide.with(binding.root)
                .load(item.images.square140)
                .error(binding.imageNewsIv.context.getDrawable(R.drawable.no_image))

                .into(binding.imageNewsIv)
            binding.titleTv.text = item.title

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

