package com.ianbl8.mymusic.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ianbl8.mymusic.databinding.CardItemBinding
import com.ianbl8.mymusic.models.ItemModel
import com.squareup.picasso.Picasso

interface ItemListener {
    fun onItemClick(item: ItemModel, position: Int)
}

class ItemAdapter constructor(
    private var items: List<ItemModel>,
    private val listener: ItemListener
) : RecyclerView.Adapter<ItemAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val item = items[holder.adapterPosition]
        holder.bind(item, listener)
    }

    override fun getItemCount(): Int = items.size

    class MainHolder(private val binding: CardItemBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: ItemModel, listener: ItemListener) {
            binding.itemTitle.text = item.title
            binding.itemArtist.text = item.artist
            binding.itemYear.text = "(${item.year})"
            if (item.cover.toString().isNotEmpty()) {
                Picasso.get().load(item.cover).centerCrop().resize(200, 200)
                    .into(binding.imageCover)
            }
            binding.root.setOnClickListener { listener.onItemClick(item, adapterPosition) }
        }
    }
}