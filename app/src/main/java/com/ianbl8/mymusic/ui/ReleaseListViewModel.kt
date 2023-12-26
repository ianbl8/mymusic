package com.ianbl8.mymusic.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ianbl8.mymusic.models.ReleaseManager
import com.ianbl8.mymusic.models.ReleaseModel

class ReleaseListViewModel: ViewModel() {

    private val releasesList = MutableLiveData<List<ReleaseModel>>()

    val observableReleasesList: LiveData<List<ReleaseModel>>
        get() = releasesList

    init {
        load()
    }

    fun load() {
        releasesList.value = ReleaseManager.findAll()
    }
}