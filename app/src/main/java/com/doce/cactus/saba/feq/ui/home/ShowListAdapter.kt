package com.doce.cactus.saba.feq.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.doce.cactus.saba.feq.R
import com.doce.cactus.saba.feq.databinding.ShowItemBinding
import com.doce.cactus.saba.feq.models.Show

class ShowListAdapter: ListAdapter<Show, ShowListAdapter.ViewHolder>(MyDiffCallback()){

    class ViewHolder private constructor( val binding: ShowItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: Show) {

            Glide.with(binding.root)
                .load(item.cover)
                .error(AppCompatResources.getDrawable(binding.imageNewsIv.context, R.drawable.no_image))
                .into(binding.imageNewsIv)
            binding.titleTv.text = item.band
        }


        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ShowItemBinding.inflate(layoutInflater, parent, false)
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

    class MyDiffCallback : DiffUtil.ItemCallback<Show>() {
        override fun areItemsTheSame(firstItem: Show, secondItem: Show) =
            firstItem.id == secondItem.id

        override fun areContentsTheSame(firstItem: Show, secondItem: Show) =
            firstItem == secondItem
    }


}