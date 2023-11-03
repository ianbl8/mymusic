package com.ianbl8.mymusic.main

import android.app.Application
import com.ianbl8.mymusic.models.ItemJSONStore
import com.ianbl8.mymusic.models.ItemMemStore
import com.ianbl8.mymusic.models.ItemModel
import com.ianbl8.mymusic.models.ItemStore
import timber.log.Timber
import timber.log.Timber.Forest.i

class MainApp: Application() {

    lateinit var items: ItemStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        // items = ItemMemStore()
        items = ItemJSONStore(this.applicationContext)
        i("mymusic started")

        // Items for testing - will be added if none exist
        // Comment out if not needed
        if (items.findAll().isEmpty()) {
            items.create(ItemModel("", "Ring Ring", "ABBA", "1973", true, true))
            items.create(ItemModel("", "Waterloo", "ABBA", "1974", true, true))
            items.create(ItemModel("", "ABBA", "ABBA", "1975", true, true))
            items.create(ItemModel("", "Arrival", "ABBA", "1976", true, true))
            items.create(ItemModel("", "The Album", "ABBA", "1977", true, true))
            items.create(ItemModel("", "Voulez-Vous", "ABBA", "1979", true, true))
            items.create(ItemModel("", "Super Trouper", "ABBA", "1980", true, true))
            items.create(ItemModel("", "The Visitors", "ABBA", "1981", true, true))
            items.create(ItemModel("", "ABBA Live", "ABBA", "1986", true, true))
            items.create(ItemModel("", "Voyage", "ABBA", "2021", true, true))
        }

    }
}