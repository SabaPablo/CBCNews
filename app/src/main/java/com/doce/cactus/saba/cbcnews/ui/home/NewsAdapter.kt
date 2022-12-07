package com.doce.cactus.saba.cbcnews.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.doce.cactus.saba.cbcnews.databinding.NewsItemBinding
import com.doce.cactus.saba.cbcnews.models.News

class NewsAdapter(private val dataSet: List<News>): RecyclerView.Adapter<NewsAdapter.ViewHolder>(){
    class ViewHolder private constructor(private val binding: NewsItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: News) {
            Glide.with(binding.root)
                .load(item.images.square140)
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
        val item = dataSet[position]
        holder.bind(item)

    }


    override fun getItemCount(): Int {
        return dataSet.size
    }
}