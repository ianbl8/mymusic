package com.ianbl8.mymusic.models

import java.util.UUID

interface ReleaseStore {
    fun findAll(): List<ReleaseModel>
    fun findById(searchId: String): ReleaseModel?
    fun findByTitle(searchTitle: String): ReleaseModel?
    fun create(release: ReleaseModel)
    fun update(release: ReleaseModel)
    fun delete(release: ReleaseModel)
    fun createTrack(release: ReleaseModel, track: TrackModel)
    fun updateTrack(release: ReleaseModel, track: TrackModel)
    fun deleteTrack(release: ReleaseModel, track: TrackModel)
    fun generateId(): String {
        return UUID.randomUUID().toString()
    }
}