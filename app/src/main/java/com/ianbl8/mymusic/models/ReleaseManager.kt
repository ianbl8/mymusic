package com.ianbl8.mymusic.models

import timber.log.Timber

object ReleaseManager: ReleaseStore {
    val releases = ArrayList<ReleaseModel>()

    override fun findAll(): List<ReleaseModel> {
        return releases
    }

    override fun findAll(email: String): List<ReleaseModel> {
        return releases
    }

    override fun findById(searchId: String): ReleaseModel? {
        val foundRelease: ReleaseModel? = releases.find { r -> r.id == searchId }
        return foundRelease
    }

    override fun findByTitle(searchTitle: String): ReleaseModel? {
        val foundRelease: ReleaseModel? = releases.find { r -> r.title == searchTitle }
        return foundRelease
    }

    override fun create(release: ReleaseModel) {
        release.id = generateId()
        releases.add(release)
        logAll()
    }

    override fun update(release: ReleaseModel) {
        val updateRelease: ReleaseModel? = releases.find { r -> r.id == release.id }
        if (updateRelease != null) {
            updateRelease.title = release.title
            updateRelease.artist = release.artist
            updateRelease.year = release.year
            updateRelease.discs = release.discs
            updateRelease.physical = release.physical
            updateRelease.digital = release.digital
            updateRelease.cover = release.cover
            updateRelease.tracks = release.tracks
            logAll()
        }
    }

    override fun delete(release: ReleaseModel) {
        releases.remove(release)
    }

    override fun findTrack(releaseId: String, trackId: String): TrackModel? {
        val foundRelease: ReleaseModel? = releases.find { r -> r.id == releaseId }
        val foundTrack: TrackModel? = foundRelease?.tracks!!.find { t -> t.id == trackId }
        return foundTrack
    }

    override fun createTrack(release: ReleaseModel, track: TrackModel) {
        val updateRelease: ReleaseModel? = releases.find { r -> r.id == release.id }
        if (updateRelease != null) {
            track.id = generateId()
            updateRelease.tracks.add(track)
            logAll()
        }
    }

    override fun updateTrack(release: ReleaseModel, track: TrackModel) {
        val updateRelease: ReleaseModel? = releases.find { r -> r.id == release.id }
        if (updateRelease != null) {
            val updateTrack: TrackModel? = updateRelease.tracks.find { t -> t.id == track.id }
            val index = updateRelease.tracks.indexOf(updateTrack)
            updateRelease.tracks[index] = track
            logAll()
        }
    }

    override fun deleteTrack(release: ReleaseModel, track: TrackModel) {
        val updateRelease: ReleaseModel? = releases.find { r -> r.id == release.id }
        if (updateRelease != null) {
            updateRelease.tracks.remove(track)
            logAll()
        }
    }

    fun logAll() {
        releases.forEach {
            Timber.i("${it}")
        }
    }
}