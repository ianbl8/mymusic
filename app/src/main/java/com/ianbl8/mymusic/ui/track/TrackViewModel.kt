package com.ianbl8.mymusic.ui.track

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ianbl8.mymusic.firebase.FirebaseDBManager
import com.ianbl8.mymusic.models.TrackModel
import timber.log.Timber

class TrackViewModel: ViewModel() {

    private val track = MutableLiveData<TrackModel>()

    val observableTrack: LiveData<TrackModel>
        get() = track

    fun findTrack(userId: String, releaseId: String, trackId: String) {
        try {
            FirebaseDBManager.findTrack(userId, releaseId, trackId, track)
            Timber.i("findTrack success: $track")
        } catch (e: Exception) {
            Timber.i("findTrack error: ${e.message}")
        }
    }

    fun createTrack(userId: String, releaseId: String, track: TrackModel) {
        try {
            FirebaseDBManager.createTrack(userId, releaseId, track)
            Timber.i("createTrack success: $track")
        } catch (e: Exception) {
            Timber.i("createTrack error: ${e.message}")
        }
    }
    fun updateTrack(userId: String, releaseId: String, trackId: String, track: TrackModel) {
        try {
            FirebaseDBManager.updateTrack(userId, releaseId, trackId, track)
            Timber.i("updateTrack success: $track")
        } catch (e: Exception) {
            Timber.i("updateTrack error: ${e.message}")
        }
    }

    fun deleteTrack(userId: String, releaseId: String, trackId: String) {
        try {
            FirebaseDBManager.deleteTrack(userId, releaseId, trackId)
            Timber.i("deleteTrack success: $track")
        } catch (e: Exception) {
            Timber.i("deleteTrack error: ${e.message}")
        }
    }

}