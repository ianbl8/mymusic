package com.ianbl8.mymusic.models

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import java.util.UUID

interface ReleaseStore {
    fun findAll(releasesList: MutableLiveData<List<ReleaseModel>>)
    fun findAll(userId: String, releasesList: MutableLiveData<List<ReleaseModel>>)
    fun findById(userId: String, releaseId: String, release: MutableLiveData<ReleaseModel>)
    fun findByTitle(userId: String, releaseTitle: String, release: MutableLiveData<ReleaseModel>)
    fun create(firebaseUser: MutableLiveData<FirebaseUser>, release: ReleaseModel)
    fun update(userId: String, releaseId: String, release: ReleaseModel)
    fun delete(userId: String, releaseId: String)

    // Track functions to be updated
    fun findTrack(userId: String, releaseId: String, trackId: String, track: MutableLiveData<TrackModel>)
    fun createTrack(userId: String, releaseId: String, track: TrackModel)
    fun updateTrack(userId: String, releaseId: String, trackId: String, track: TrackModel)
    fun deleteTrack(userId: String, releaseId: String, trackId: String)
    fun generateId(): String {
        return UUID.randomUUID().toString()
    }
}