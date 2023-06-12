package com.doce.cactus.saba.feq.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.doce.cactus.saba.feq.databinding.DayItemBinding
import com.doce.cactus.saba.feq.listener.OnIntClickListener
import com.doce.cactus.saba.feq.listener.OnShowClickListener

class DayListAdapter(val listener: OnIntClickListener): ListAdapter<Int, DayListAdapter.ViewHolder>(MyDiffCallback()){

    class ViewHolder private constructor( val binding: DayItemBinding, val listener: OnIntClickListener) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: Int) {
            binding.titleTv.text = "Dia $item"
            binding.root.setOnClickListener {
                listener.onItemClick(item)
            }
        }


        companion object {
            fun from(parent: ViewGroup, listener: OnIntClickListener): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = DayItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding, listener)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent, listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class MyDiffCallback : DiffUtil.ItemCallback<Int>() {
        override fun areItemsTheSame(firstItem: Int, secondItem: Int) =
            firstItem == secondItem

        override fun areContentsTheSame(firstItem: Int, secondItem: Int) =
            firstItem == secondItem
    }


}