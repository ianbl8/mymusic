package com.ianbl8.mymusic.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ianbl8.mymusic.databinding.CardTrackBinding
import com.ianbl8.mymusic.models.TrackModel

interface TrackListener {
        fun onTrackClick(track: TrackModel, position: Int)
}

class TrackAdapter constructor(
    private var tracks: List<TrackModel>,
    private val listener: TrackListener
) : RecyclerView.Adapter<TrackAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardTrackBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val track = tracks[holder.adapterPosition]
        holder.bind(track, listener)
    }

    override fun getItemCount(): Int = tracks.size

    class MainHolder(private val binding: CardTrackBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(track: TrackModel, listener: TrackListener) {
            binding.trackDiscNumber.text = track.discNumber.toString()
            binding.trackTrackNumber.text = track.trackNumber.toString()
            binding.trackTitle.text = track.trackTitle
            binding.trackArtist.text = track.trackArtist
            binding.root.setOnClickListener { listener.onTrackClick(track, adapterPosition) }
        }
    }

}