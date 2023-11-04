package com.ianbl8.mymusic.models

import timber.log.Timber.Forest.i

class TrackMemStore: TrackStore {
    val tracks = ArrayList<TrackModel>()

    override fun findAll(): List<TrackModel> {
        return tracks
    }

    override fun create(track: TrackModel) {
        track.id = generateId()
        tracks.add(track)
        logAll()
    }

    override fun update(track: TrackModel) {
        val updateTrack: TrackModel? = tracks.find { i -> i.id == track.id }
        if (updateTrack != null) {
            updateTrack.discNumber = track.discNumber
            updateTrack.trackNumber = track.trackNumber
            updateTrack.trackName = track.trackName
            updateTrack.trackArtist = track.trackArtist
            logAll()
        }
    }

    override fun delete(track: TrackModel) {
        tracks.remove(track)
    }

    fun logAll() {
        tracks.forEach {
            i("${it}")
        }
    }
}