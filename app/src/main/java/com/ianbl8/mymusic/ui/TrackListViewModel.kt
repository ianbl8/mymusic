package com.ianbl8.mymusic.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ianbl8.mymusic.models.TrackModel

class TrackListViewModel: ViewModel() {

    private val tracksList = MutableLiveData<List<TrackModel>>()

    val observableTracksList: LiveData<List<TrackModel>>
        get() = tracksList

}