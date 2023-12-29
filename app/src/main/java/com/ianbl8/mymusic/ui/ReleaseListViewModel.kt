package com.ianbl8.mymusic.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ianbl8.mymusic.models.ReleaseManager
import com.ianbl8.mymusic.models.ReleaseModel
import timber.log.Timber

class ReleaseListViewModel: ViewModel() {

    private val releasesList = MutableLiveData<List<ReleaseModel>>()

    val observableReleasesList: LiveData<List<ReleaseModel>>
        get() = releasesList

    init {
        loadAll()
    }

    fun loadAll() {
        try {
            releasesList.value = ReleaseManager.findAll()
            Timber.i("loadAll success")
        } catch (e: Exception) {
            Timber.i("loadAll error: ${e.message}")
        }
    }
}