package com.ianbl8.mymusic.main

import android.app.Application
import com.ianbl8.mymusic.models.ItemModel
import timber.log.Timber
import timber.log.Timber.Forest.i

class MainApp: Application() {

    val items = ArrayList<ItemModel>()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("mymusic started")
    }
}