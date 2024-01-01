package com.ianbl8.mymusic.ui.release

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.ianbl8.mymusic.firebase.FirebaseDBManager
import com.ianbl8.mymusic.models.ReleaseModel
import timber.log.Timber

class ReleaseViewModel: ViewModel() {

    private val release = MutableLiveData<ReleaseModel>()
    var observableRelease: LiveData<ReleaseModel>
        get() = release
        set(value) { release.value = value.value }

    private val status = MutableLiveData<Boolean>()
    val observableStatus: LiveData<Boolean>
        get() = status

    fun findReleaseById(userId: String, id: String) {
        try {
            FirebaseDBManager.findById(userId, id, release)
            Timber.i("RVM findRelease success: ${release.value.toString()}")
        } catch (e: Exception) {
            Timber.e("RVM findRelease error: ${e.message}")
        }
    }

    fun createRelease(firebaseUser: MutableLiveData<FirebaseUser>, release: ReleaseModel) {
        // status.value = try {
        try {
            FirebaseDBManager.create(firebaseUser, release)
            Timber.i("createRelease success: $release")
            // true
        } catch (e: Exception) {
            Timber.e("createRelease error: ${e.message}")
            // false
        }
    }
    fun updateRelease(userId: String, releaseId: String, release: ReleaseModel) {
        try {
            FirebaseDBManager.update(userId, releaseId, release)
            Timber.i("updateRelease success: $release")
        } catch (e: Exception) {
            Timber.e("updateRelease error: ${e.message}")
        }
    }

    fun deleteRelease(userId: String, releaseId: String) {
        try {
            FirebaseDBManager.delete(userId, releaseId)
            Timber.i("deleteRelease success: $releaseId")
        } catch (e: Exception) {
            Timber.e("deleteRelease error: ${e.message}")
        }
    }
}