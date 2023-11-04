package com.ianbl8.mymusic.main

import android.app.Application
import com.ianbl8.mymusic.models.ItemJSONStore
import com.ianbl8.mymusic.models.ItemMemStore
import com.ianbl8.mymusic.models.ItemModel
import com.ianbl8.mymusic.models.ItemStore
import com.ianbl8.mymusic.models.TrackJSONStore
import com.ianbl8.mymusic.models.TrackMemStore
import com.ianbl8.mymusic.models.TrackModel
import com.ianbl8.mymusic.models.TrackStore
import timber.log.Timber
import timber.log.Timber.Forest.i

class MainApp: Application() {

    lateinit var items: ItemStore
    lateinit var tracks: TrackStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        // items = ItemMemStore()
        // tracks = TrackMemStore()
        items = ItemJSONStore(this.applicationContext)
        tracks = TrackJSONStore(this.applicationContext)
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

        // Tracks for testing - will be added if none exist
        // "uuid" is a placeholder and UUIDs will be added
        // Comment out if not needed
        if (tracks.findAll().isEmpty()) {
            tracks.create(TrackModel("uuid", 1, 1, "I Still Have Faith In You", "ABBA"))
            tracks.create(TrackModel("uuid", 1, 2, "When You Danced With Me", "ABBA"))
            tracks.create(TrackModel("uuid", 1, 3, "Little Things", "ABBA"))
            tracks.create(TrackModel("uuid", 1, 4, "Don't Shut Me Down", "ABBA"))
            tracks.create(TrackModel("uuid", 1, 5, "Just A Notion", "ABBA"))
            tracks.create(TrackModel("uuid", 1, 6, "I Can Be That Woman", "ABBA"))
            tracks.create(TrackModel("uuid", 1, 7, "Keep An Eye On Dan", "ABBA"))
            tracks.create(TrackModel("uuid", 1, 8, "Bumbleebee", "ABBA"))
            tracks.create(TrackModel("uuid", 1, 9, "No Doubt ABout It", "ABBA"))
            tracks.create(TrackModel("uuid", 1, 10, "Ode To Freedom", "ABBA"))
        }

    }
}