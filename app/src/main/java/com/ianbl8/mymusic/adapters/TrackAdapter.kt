package com.ianbl8.mymusic.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ianbl8.mymusic.databinding.CardTrackBinding
import com.ianbl8.mymusic.firebase.FirebaseDBManager
import com.ianbl8.mymusic.models.ReleaseModel
import com.ianbl8.mymusic.models.TrackModel

interface TrackListener {
        fun onTrackClick(release: ReleaseModel, track: TrackModel)
}

class TrackAdapter constructor(
    private val release: ReleaseModel,
    private var tracks: ArrayList<TrackModel>,
    private val listener: TrackListener,
    private val readOnly: Boolean
) : RecyclerView.Adapter<TrackAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardTrackBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val track = tracks[holder.adapterPosition]
        holder.bind(release, track, listener)
    }

    override fun getItemCount(): Int = tracks.size

    fun removeAt(position: Int) {
        release.tracks.removeAt(position)
        notifyItemRemoved(position)
    }

    inner class MainHolder(private val binding: CardTrackBinding) : RecyclerView.ViewHolder(binding.root) {
        val readOnlyRow = readOnly
        fun bind(release: ReleaseModel, track: TrackModel, listener: TrackListener) {
            val releaseid = release.uid
            val trackid = track.uid
            val ridtid = releaseid.plus("#").plus(trackid)
            binding.root.tag = ridtid
            binding.trackDiscNumber.text = track.discNumber.toString()
            binding.trackTrackNumber.text = track.trackNumber.toString()
            binding.trackTitle.text = track.trackTitle
            binding.trackArtist.text = track.trackArtist
            binding.root.setOnClickListener { listener.onTrackClick(release, track) }
        }
    }

}