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
        // "uuid" is a placeholder and UUIDs will be added
        // Comment out if not needed
        if (items.findAll().isEmpty()) {
            items.create(ItemModel("uuid", "Ring Ring", "ABBA", "1973", 1, true, true))
            items.create(ItemModel("uuid", "Waterloo", "ABBA", "1974", 1, true, true))
            items.create(ItemModel("uuid", "ABBA", "ABBA", "1975", 1, true, true))
            items.create(ItemModel("uuid", "Arrival", "ABBA", "1976", 1, true, true))
            items.create(ItemModel("uuid", "The Album", "ABBA", "1977", 1, true, true))
            items.create(ItemModel("uuid", "Voulez-Vous", "ABBA", "1979", 1, true, true))
            items.create(ItemModel("uuid", "Super Trouper", "ABBA", "1980", 1, true, true))
            items.create(ItemModel("uuid", "The Visitors", "ABBA", "1981", 1, true, true))
            items.create(ItemModel("uuid", "ABBA Live", "ABBA", "1986", 1, true, true))
            items.create(ItemModel("uuid", "Voyage", "ABBA", "2021", 1, true, true))
        }

    }
}