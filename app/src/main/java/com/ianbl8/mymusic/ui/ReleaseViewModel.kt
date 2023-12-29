package com.ianbl8.mymusic.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ianbl8.mymusic.models.ReleaseManager
import com.ianbl8.mymusic.models.ReleaseModel
import timber.log.Timber

class ReleaseViewModel: ViewModel() {

    private val release = MutableLiveData<ReleaseModel>()

    val observableRelease: LiveData<ReleaseModel>
        get() = release

    fun findReleaseById(id: String) {
        try {
            ReleaseManager.findById(id)
            Timber.i("findRelease success: $release")
        } catch (e: Exception) {
            Timber.i("findRelease error: ${e.message}")
        }
    }

    fun createRelease(release: ReleaseModel) {
        try {
            ReleaseManager.create(release)
            Timber.i("createRelease success: $release")
        } catch (e: Exception) {
            Timber.i("createRelease error: ${e.message}")
        }
    }
    fun updateRelease(release: ReleaseModel) {
        try {
            ReleaseManager.update(release)
            Timber.i("updateRelease success: $release")
        } catch (e: Exception) {
            Timber.i("updateRelease error: ${e.message}")
        }
    }

    fun deleteRelease(release: ReleaseModel) {
        try {
            ReleaseManager.delete(release)
            Timber.i("deleteRelease success: $release")
        } catch (e: Exception) {
            Timber.i("deleteRelease error: ${e.message}")
        }
    }
}