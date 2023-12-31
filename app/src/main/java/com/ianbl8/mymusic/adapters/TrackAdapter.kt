package com.ianbl8.mymusic.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ianbl8.mymusic.databinding.CardTrackBinding
import com.ianbl8.mymusic.models.ReleaseManager
import com.ianbl8.mymusic.models.ReleaseModel
import com.ianbl8.mymusic.models.TrackModel

interface TrackListener {
        fun onTrackClick(release: ReleaseModel, track: TrackModel)
}

class TrackAdapter constructor(
    private var tracks: ArrayList<TrackModel>,
    private val listener: TrackListener
) : RecyclerView.Adapter<TrackAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardTrackBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val track = tracks[holder.adapterPosition]
        val releases = ReleaseManager.findAll()
        val release: ReleaseModel? = releases.find { r -> r.tracks == tracks }
        holder.bind(release!!, track, listener)
    }

    override fun getItemCount(): Int = tracks.size

    fun removeAt(position: Int) {
        val releases = ReleaseManager.findAll()
        val release: ReleaseModel? = releases.find { r -> r.tracks == tracks }
        release!!.tracks.removeAt(position)
        notifyItemRemoved(position)
    }

    class MainHolder(private val binding: CardTrackBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(release: ReleaseModel, track: TrackModel, listener: TrackListener) {
            binding.trackDiscNumber.text = track.discNumber.toString()
            binding.trackTrackNumber.text = track.trackNumber.toString()
            binding.trackTitle.text = track.trackTitle
            binding.trackArtist.text = track.trackArtist
            binding.root.setOnClickListener { listener.onTrackClick(release, track) }
        }
    }

}