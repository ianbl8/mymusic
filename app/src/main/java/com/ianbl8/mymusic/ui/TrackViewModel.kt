package com.ianbl8.mymusic.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ianbl8.mymusic.models.ReleaseManager
import com.ianbl8.mymusic.models.ReleaseModel
import com.ianbl8.mymusic.models.TrackModel
import timber.log.Timber

class TrackViewModel: ViewModel() {

    private val track = MutableLiveData<TrackModel>()

    val observableTrack: LiveData<TrackModel>
        get() = track

    fun findTrack(releaseId: String, trackId: String) {
        try {
            ReleaseManager.findTrack(releaseId, trackId)
            Timber.i("findTrack success: $track")
        } catch (e: Exception) {
            Timber.i("findTrack error: ${e.message}")
        }
    }

    fun createTrack(release: ReleaseModel, track: TrackModel) {
        try {
            ReleaseManager.createTrack(release, track)
            Timber.i("createTrack success: $track")
        } catch (e: Exception) {
            Timber.i("createTrack error: ${e.message}")
        }
    }
    fun updateTrack(release: ReleaseModel, track: TrackModel) {
        try {
            ReleaseManager.updateTrack(release, track)
            Timber.i("updateTrack success: $track")
        } catch (e: Exception) {
            Timber.i("updateTrack error: ${e.message}")
        }
    }

    fun deleteTrack(release: ReleaseModel, track: TrackModel) {
        try {
            ReleaseManager.deleteTrack(release, track)
            Timber.i("deleteTrack success: $track")
        } catch (e: Exception) {
            Timber.i("deleteTrack error: ${e.message}")
        }
    }

}