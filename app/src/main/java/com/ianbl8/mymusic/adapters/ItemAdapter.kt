package com.ianbl8.mymusic.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ianbl8.mymusic.databinding.CardItemBinding
import com.ianbl8.mymusic.models.ItemModel

class ItemAdapter constructor(private var items: List<ItemModel>) : RecyclerView.Adapter<ItemAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val item = items[holder.adapterPosition]
        holder.bind(item)
    }

    override fun getItemCount(): Int = items.size

    class MainHolder(private val binding: CardItemBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: ItemModel) {
            binding.itemTitle.text = item.title
            binding.itemArtist.text = item.artist
            binding.itemYear.text = "(${item.year})"
        }
    }
}