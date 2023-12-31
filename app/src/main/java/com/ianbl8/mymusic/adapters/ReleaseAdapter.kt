package com.ianbl8.mymusic.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ianbl8.mymusic.databinding.CardReleaseBinding
import com.ianbl8.mymusic.models.ReleaseModel
import com.squareup.picasso.Picasso

interface ReleaseListener {
    fun onReleaseClick(release: ReleaseModel)
}

class ReleaseAdapter constructor(
    private var releases: ArrayList<ReleaseModel>,
    private val listener: ReleaseListener
) : RecyclerView.Adapter<ReleaseAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardReleaseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val release = releases[holder.adapterPosition]
        holder.bind(release, listener)
    }

    override fun getItemCount(): Int = releases.size

    fun removeAt(position: Int) {
        releases.removeAt(position)
        notifyItemRemoved(position)
    }

    inner class MainHolder(val binding: CardReleaseBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(release: ReleaseModel, listener: ReleaseListener) {
            binding.root.tag = release
            binding.release = release
            if (release.cover.toString().isNotEmpty()) {
                Picasso.get().load(release.cover).centerCrop().resize(200, 200)
                    .into(binding.imageCover)
            }
            binding.root.setOnClickListener { listener.onReleaseClick(release) }
            binding.executePendingBindings()
        }
    }
}