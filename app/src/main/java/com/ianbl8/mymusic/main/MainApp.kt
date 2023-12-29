package com.ianbl8.mymusic.main

import android.app.Application
import com.ianbl8.mymusic.models.ReleaseJSONStore
import com.ianbl8.mymusic.models.ReleaseModel
import com.ianbl8.mymusic.models.ReleaseStore
import com.ianbl8.mymusic.models.TrackModel
import timber.log.Timber
import timber.log.Timber.Forest.i

class MainApp: Application() {

    lateinit var releases: ReleaseStore
    lateinit var release: ReleaseModel

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        // releases = ReleaseMemStore()
        // releases = ReleaseJSONStore(this.applicationContext)
        i("mymusic started")
    }

    fun sampleData() {

        // Ring Ring
        releases.create(ReleaseModel("uuid", "Ring Ring", "ABBA", "1973", 1, true, true))
        release = releases.findByTitle("Ring Ring")!!
        i(release.toString())
        if (release.id.isNotEmpty()) {
            releases.createTrack(release, TrackModel("uuid", 1, 1, "Ring Ring", "ABBA"))
            releases.createTrack(release, TrackModel("uuid", 1, 2, "Another Town, Another Train", "ABBA"))
            releases.createTrack(release, TrackModel("uuid", 1, 3, "Disillusion", "ABBA"))
            releases.createTrack(release, TrackModel("uuid", 1, 4, "People Need Love", "ABBA"))
            releases.createTrack(release, TrackModel("uuid", 1, 5, "I Saw It In The Mirror", "ABBA"))
            releases.createTrack(release, TrackModel("uuid", 1, 6, "Nina, Pretty Ballerina", "ABBA"))
            releases.createTrack(release, TrackModel("uuid", 1, 7, "Love Isn't Easy (But It Sure Is Hard Enough)", "ABBA"))
            releases.createTrack(release, TrackModel("uuid", 1, 8, "Me And Bobby And Bobby's Brother", "ABBA"))
            releases.createTrack(release, TrackModel("uuid", 1, 9, "He Is Your Brother", "ABBA"))
            releases.createTrack(release, TrackModel("uuid", 1, 10, "She's My Kind Of Girl", "ABBA"))
            releases.createTrack(release, TrackModel("uuid", 1, 11, "I Am Just A Girl", "ABBA"))
            releases.createTrack(release, TrackModel("uuid", 1, 12, "Rock 'N Roll Band", "ABBA"))
        }

        // Waterloo
        releases.create(ReleaseModel("uuid", "Waterloo", "ABBA", "1974", 1, true, true))
        release = releases.findByTitle("Waterloo")!!
        i(release.toString())
        if (release.id.isNotEmpty()) {
            releases.createTrack(release, TrackModel("uuid", 1, 1, "Waterloo", "ABBA"))
            releases.createTrack(release, TrackModel("uuid", 1, 2, "Sitting In The Palmtree", "ABBA"))
            releases.createTrack(release, TrackModel("uuid", 1, 3, "King Kong Song", "ABBA"))
            releases.createTrack(release, TrackModel("uuid", 1, 4, "Hasta Mañana", "ABBA"))
            releases.createTrack(release, TrackModel("uuid", 1, 5, "My Mama Said", "ABBA"))
            releases.createTrack(release, TrackModel("uuid", 1, 6, "Dance (While The Music Still Goes On)", "ABBA"))
            releases.createTrack(release, TrackModel("uuid", 1, 7, "Honey, Honey", "ABBA"))
            releases.createTrack(release, TrackModel("uuid", 1, 8, "Watch Out", "ABBA"))
            releases.createTrack(release, TrackModel("uuid", 1, 9, "What About Livingstone", "ABBA"))
            releases.createTrack(release, TrackModel("uuid", 1, 10, "Gonna Sing You My Lovesong", "ABBA"))
            releases.createTrack(release, TrackModel("uuid", 1, 11, "Suzy-Hang-Around", "ABBA"))
            releases.createTrack(release, TrackModel("uuid", 1, 12, "Ring Ring", "ABBA"))
        }

        // ABBA
        releases.create(ReleaseModel("uuid", "ABBA", "ABBA", "1975", 1, true, true))
        release = releases.findByTitle("ABBA")!!
        i(release.toString())
        if (release.id.isNotEmpty()) {
            releases.createTrack(release, TrackModel("uuid", 1, 1, "Mamma Mia", "ABBA"))
            releases.createTrack(release, TrackModel("uuid", 1, 2, "Hey, Hey Helen", "ABBA"))
            releases.createTrack(release, TrackModel("uuid", 1, 3, "Tropical Loveland", "ABBA"))
            releases.createTrack(release, TrackModel("uuid", 1, 4, "SOS", "ABBA"))
            releases.createTrack(release, TrackModel("uuid", 1, 5, "Man In The Middle", "ABBA"))
            releases.createTrack(release, TrackModel("uuid", 1, 6, "Bang-A-Boomerang", "ABBA"))
            releases.createTrack(release, TrackModel("uuid", 1, 7, "I Do, I Do, I Do, I Do, I Do", "ABBA"))
            releases.createTrack(release, TrackModel("uuid", 1, 8, "Rock Me", "ABBA"))
            releases.createTrack(release, TrackModel("uuid", 1, 9, "Intermezzo No. 1", "ABBA"))
            releases.createTrack(release, TrackModel("uuid", 1, 10, "I've Been Waiting For You", "ABBA"))
            releases.createTrack(release, TrackModel("uuid", 1, 11, "So Long", "ABBA"))
        }

        // Arrival
        releases.create(ReleaseModel("uuid", "Arrival", "ABBA", "1976", 1, true, true))
        release = releases.findByTitle("Arrival")!!
        i(release.toString())
        if (release.id.isNotEmpty()) {
            releases.createTrack(release, TrackModel("uuid", 1, 1, "When I Kissed The Teacher", "ABBA"))
            releases.createTrack(release, TrackModel("uuid", 1, 2, "Dancing Queen", "ABBA"))
            releases.createTrack(release, TrackModel("uuid", 1, 3, "My Love, My Life", "ABBA"))
            releases.createTrack(release, TrackModel("uuid", 1, 4, "Dum Dum Diddle", "ABBA"))
            releases.createTrack(release, TrackModel("uuid", 1, 5, "Knowing Me, Knowing You", "ABBA"))
            releases.createTrack(release, TrackModel("uuid", 1, 6, "Money, Money, Money", "ABBA"))
            releases.createTrack(release, TrackModel("uuid", 1, 7, "That's Me", "ABBA"))
            releases.createTrack(release, TrackModel("uuid", 1, 8, "Why Did It Have To Be Me", "ABBA"))
            releases.createTrack(release, TrackModel("uuid", 1, 9, "Tiger", "ABBA"))
            releases.createTrack(release, TrackModel("uuid", 1, 10, "Arrival", "ABBA"))
        }

        // The Album
        releases.create(ReleaseModel("uuid", "The Album", "ABBA", "1977", 1, true, true))
        release = releases.findByTitle("The Album")!!
        i(release.toString())
        if (release.id.isNotEmpty()) {
            releases.createTrack(release, TrackModel("uuid", 1, 1, "Eagle", "ABBA"))
            releases.createTrack(release, TrackModel("uuid", 1, 2, "Take A Chance On Me", "ABBA"))
            releases.createTrack(release, TrackModel("uuid", 1, 3, "One Man, One Woman", "ABBA"))
            releases.createTrack(release, TrackModel("uuid", 1, 4, "The Name Of The Game", "ABBA"))
            releases.createTrack(release, TrackModel("uuid", 1, 5, "Move On", "ABBA"))
            releases.createTrack(release, TrackModel("uuid", 1, 6, "Hole In Your Soul", "ABBA"))
            releases.createTrack(release, TrackModel("uuid", 1, 7, "Thank You For The Music", "ABBA"))
            releases.createTrack(release, TrackModel("uuid", 1, 8, "I Wonder (Departure)", "ABBA"))
            releases.createTrack(release, TrackModel("uuid", 1, 9, "I'm A Marionette", "ABBA"))
        }

        // Voulez-Vous
        releases.create(ReleaseModel("uuid", "Voulez-Vous", "ABBA", "1979", 1, true, true))
        release = releases.findByTitle("Voulez-Vous")!!
        i(release.toString())
        if (release.id.isNotEmpty()) {
            releases.createTrack(release, TrackModel("uuid", 1, 1, "As Good As New", "ABBA"))
            releases.createTrack(release, TrackModel("uuid", 1, 2, "Voulez-Vous", "ABBA"))
            releases.createTrack(release, TrackModel("uuid", 1, 3, "I Have A Dream", "ABBA"))
            releases.createTrack(release, TrackModel("uuid", 1, 4, "Angeleyes", "ABBA"))
            releases.createTrack(release, TrackModel("uuid", 1, 5, "The King Has Lost His Crown", "ABBA"))
            releases.createTrack(release, TrackModel("uuid", 1, 6, "Does Your Mother Know", "ABBA"))
            releases.createTrack(release, TrackModel("uuid", 1, 7, "If It Wasn't For The Nights", "ABBA"))
            releases.createTrack(release, TrackModel("uuid", 1, 8, "Chiquitita", "ABBA"))
            releases.createTrack(release, TrackModel("uuid", 1, 9, "Lovers (Live A Little Longer)", "ABBA"))
            releases.createTrack(release, TrackModel("uuid", 1, 10, "Kisses Of Fire", "ABBA"))
        }

        // Super Trouper
        releases.create(ReleaseModel("uuid", "Super Trouper", "ABBA", "1980", 1, true, true))
        release = releases.findByTitle("Super Trouper")!!
        i(release.toString())
        if (release.id.isNotEmpty()) {
            releases.createTrack(release, TrackModel("uuid", 1, 1, "Super Trouper", "ABBA"))
            releases.createTrack(release, TrackModel("uuid", 1, 2, "The Winner Takes It All", "ABBA"))
            releases.createTrack(release, TrackModel("uuid", 1, 3, "On And On And On", "ABBA"))
            releases.createTrack(release, TrackModel("uuid", 1, 4, "Andante, Andante", "ABBA"))
            releases.createTrack(release, TrackModel("uuid", 1, 5, "Me And I", "ABBA"))
            releases.createTrack(release, TrackModel("uuid", 1, 6, "Happy New Year", "ABBA"))
            releases.createTrack(release, TrackModel("uuid", 1, 7, "Our Last Summer", "ABBA"))
            releases.createTrack(release, TrackModel("uuid", 1, 8, "The Piper", "ABBA"))
            releases.createTrack(release, TrackModel("uuid", 1, 9, "Lay All Your Love On Me", "ABBA"))
            releases.createTrack(release, TrackModel("uuid", 1, 10, "The Way Old Friends Do (live)", "ABBA"))
        }

        // The Visitors
        releases.create(ReleaseModel("uuid", "The Visitors", "ABBA", "1981", 1, true, true))
        release = releases.findByTitle("The Visitors")!!
        i(release.toString())
        if (release.id.isNotEmpty()) {
            releases.createTrack(release, TrackModel("uuid", 1, 1, "The Visitors", "ABBA"))
            releases.createTrack(release, TrackModel("uuid", 1, 2, "Head Over Heels", "ABBA"))
            releases.createTrack(release, TrackModel("uuid", 1, 3, "When All Is Said And Done", "ABBA"))
            releases.createTrack(release, TrackModel("uuid", 1, 4, "Soldiers", "ABBA"))
            releases.createTrack(release, TrackModel("uuid", 1, 5, "I Let The Music Speak", "ABBA"))
            releases.createTrack(release, TrackModel("uuid", 1, 6, "One Of Us", "ABBA"))
            releases.createTrack(release, TrackModel("uuid", 1, 7, "Two For The Price Of One", "ABBA"))
            releases.createTrack(release, TrackModel("uuid", 1, 8, "Slipping Through My Fingers", "ABBA"))
            releases.createTrack(release, TrackModel("uuid", 1, 9, "Like An Angel Passing Through My Room", "ABBA"))
        }

        // Voyage
        releases.create(ReleaseModel("uuid", "Voyage", "ABBA", "2021", 1, true, true))
        release = releases.findByTitle("Voyage")!!
        i(release.toString())
        if (release.id.isNotEmpty()) {
            releases.createTrack(release, TrackModel("uuid", 1, 1, "I Still Have Faith In You", "ABBA"))
            releases.createTrack(release, TrackModel("uuid", 1, 2, "When You Danced With Me", "ABBA"))
            releases.createTrack(release, TrackModel("uuid", 1, 3, "Little Things", "ABBA"))
            releases.createTrack(release, TrackModel("uuid", 1, 4, "Don't Shut Me Down", "ABBA"))
            releases.createTrack(release, TrackModel("uuid", 1, 5, "Just A Notion", "ABBA"))
            releases.createTrack(release, TrackModel("uuid", 1, 6, "I Can Be That Woman", "ABBA"))
            releases.createTrack(release, TrackModel("uuid", 1, 7, "Keep An Eye On Dan", "ABBA"))
            releases.createTrack(release, TrackModel("uuid", 1, 8, "Bumblebee", "ABBA"))
            releases.createTrack(release, TrackModel("uuid", 1, 9, "No Doubt About It", "ABBA"))
            releases.createTrack(release, TrackModel("uuid", 1, 10, "Ode To Freedom", "ABBA"))
        }
    }
}