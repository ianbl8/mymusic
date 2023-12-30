package com.ianbl8.mymusic.main

import android.app.Application
import com.ianbl8.mymusic.models.ReleaseModel
import timber.log.Timber
import timber.log.Timber.Forest.i

class MainApp: Application() {

    // lateinit var releases: ReleaseStore
    lateinit var release: ReleaseModel

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        // releases = ReleaseMemStore()
        // releases = ReleaseJSONStore(this.applicationContext)
        i("mymusic started")
    }
}