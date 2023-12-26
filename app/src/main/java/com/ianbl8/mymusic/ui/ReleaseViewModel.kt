package com.ianbl8.mymusic.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ianbl8.mymusic.models.ReleaseManager
import com.ianbl8.mymusic.models.ReleaseModel
import java.lang.IllegalArgumentException

class ReleaseViewModel: ViewModel() {

    private val status = MutableLiveData<Boolean>()

    val observableStatus: LiveData<Boolean>
        get() = status

    fun createRelease(release: ReleaseModel) {
        status.value = try {
            ReleaseManager.create(release)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }
    fun updateRelease(release: ReleaseModel) {
        status.value = try {
            ReleaseManager.update(release)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }
}