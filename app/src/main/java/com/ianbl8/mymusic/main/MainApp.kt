package com.ianbl8.mymusic.main

import android.app.Application
import com.ianbl8.mymusic.models.ReleaseJSONStore
import com.ianbl8.mymusic.models.ReleaseModel
import com.ianbl8.mymusic.models.ReleaseStore
import com.ianbl8.mymusic.models.TrackJSONStore
import com.ianbl8.mymusic.models.TrackModel
import com.ianbl8.mymusic.models.TrackStore
import timber.log.Timber
import timber.log.Timber.Forest.i

class MainApp: Application() {

    lateinit var releases: ReleaseStore
    lateinit var tracks: TrackStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        // releases = ReleaseMemStore()
        // tracks = TrackMemStore()
        releases = ReleaseJSONStore(this.applicationContext)
        tracks = TrackJSONStore(this.applicationContext)
        i("mymusic started")

        // Releases for testing - will be added if none exist
        // "uuid" is a placeholder and UUIDs will be added
        // Comment out if not needed
        if (releases.findAll().isEmpty()) {
            releases.create(ReleaseModel("uuid", "Ring Ring", "ABBA", "1973", 1, true, true))
            releases.create(ReleaseModel("uuid", "Waterloo", "ABBA", "1974", 1, true, true))
            releases.create(ReleaseModel("uuid", "ABBA", "ABBA", "1975", 1, true, true))
            releases.create(ReleaseModel("uuid", "Arrival", "ABBA", "1976", 1, true, true))
            releases.create(ReleaseModel("uuid", "The Album", "ABBA", "1977", 1, true, true))
            releases.create(ReleaseModel("uuid", "Voulez-Vous", "ABBA", "1979", 1, true, true))
            releases.create(ReleaseModel("uuid", "Super Trouper", "ABBA", "1980", 1, true, true))
            releases.create(ReleaseModel("uuid", "The Visitors", "ABBA", "1981", 1, true, true))
            releases.create(ReleaseModel("uuid", "ABBA Live", "ABBA", "1986", 1, true, true))
            releases.create(ReleaseModel("uuid", "Voyage", "ABBA", "2021", 1, true, true))
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
            tracks.create(TrackModel("uuid", 1, 8, "Bumblebee", "ABBA"))
            tracks.create(TrackModel("uuid", 1, 9, "No Doubt ABout It", "ABBA"))
            tracks.create(TrackModel("uuid", 1, 10, "Ode To Freedom", "ABBA"))
        }

    }
}