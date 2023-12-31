package com.ianbl8.mymusic.ui.releaselist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ianbl8.mymusic.models.ReleaseManager
import com.ianbl8.mymusic.models.ReleaseModel
import com.ianbl8.mymusic.models.TrackModel
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

    fun sampleData() {
        var release: ReleaseModel

        // Ring Ring
        ReleaseManager.create(ReleaseModel("uuid", "Ring Ring", "ABBA", "1973", 1, true, true))
        release = ReleaseManager.findByTitle("Ring Ring")!!
        Timber.Forest.i(release.toString())
        if (release.id.isNotEmpty()) {
            ReleaseManager.createTrack(release, TrackModel("uuid", 1, 1, "Ring Ring", "ABBA"))
            ReleaseManager.createTrack(release, TrackModel("uuid", 1, 2, "Another Town, Another Train", "ABBA"))
            ReleaseManager.createTrack(release, TrackModel("uuid", 1, 3, "Disillusion", "ABBA"))
            ReleaseManager.createTrack(release, TrackModel("uuid", 1, 4, "People Need Love", "ABBA"))
            ReleaseManager.createTrack(release, TrackModel("uuid", 1, 5, "I Saw It In The Mirror", "ABBA"))
            ReleaseManager.createTrack(release, TrackModel("uuid", 1, 6, "Nina, Pretty Ballerina", "ABBA"))
            ReleaseManager.createTrack(release, TrackModel("uuid", 1, 7, "Love Isn't Easy (But It Sure Is Hard Enough)", "ABBA"))
            ReleaseManager.createTrack(release, TrackModel("uuid", 1, 8, "Me And Bobby And Bobby's Brother", "ABBA"))
            ReleaseManager.createTrack(release, TrackModel("uuid", 1, 9, "He Is Your Brother", "ABBA"))
            ReleaseManager.createTrack(release, TrackModel("uuid", 1, 10, "She's My Kind Of Girl", "ABBA"))
            ReleaseManager.createTrack(release, TrackModel("uuid", 1, 11, "I Am Just A Girl", "ABBA"))
            ReleaseManager.createTrack(release, TrackModel("uuid", 1, 12, "Rock 'N Roll Band", "ABBA"))
        }

        // Waterloo
        ReleaseManager.create(ReleaseModel("uuid", "Waterloo", "ABBA", "1974", 1, true, true))
        release = ReleaseManager.findByTitle("Waterloo")!!
        Timber.Forest.i(release.toString())
        if (release.id.isNotEmpty()) {
            ReleaseManager.createTrack(release, TrackModel("uuid", 1, 1, "Waterloo", "ABBA"))
            ReleaseManager.createTrack(release, TrackModel("uuid", 1, 2, "Sitting In The Palmtree", "ABBA"))
            ReleaseManager.createTrack(release, TrackModel("uuid", 1, 3, "King Kong Song", "ABBA"))
            ReleaseManager.createTrack(release, TrackModel("uuid", 1, 4, "Hasta Mañana", "ABBA"))
            ReleaseManager.createTrack(release, TrackModel("uuid", 1, 5, "My Mama Said", "ABBA"))
            ReleaseManager.createTrack(release, TrackModel("uuid", 1, 6, "Dance (While The Music Still Goes On)", "ABBA"))
            ReleaseManager.createTrack(release, TrackModel("uuid", 1, 7, "Honey, Honey", "ABBA"))
            ReleaseManager.createTrack(release, TrackModel("uuid", 1, 8, "Watch Out", "ABBA"))
            ReleaseManager.createTrack(release, TrackModel("uuid", 1, 9, "What About Livingstone", "ABBA"))
            ReleaseManager.createTrack(release, TrackModel("uuid", 1, 10, "Gonna Sing You My Lovesong", "ABBA"))
            ReleaseManager.createTrack(release, TrackModel("uuid", 1, 11, "Suzy-Hang-Around", "ABBA"))
            ReleaseManager.createTrack(release, TrackModel("uuid", 1, 12, "Ring Ring", "ABBA"))
        }

        // ABBA
        ReleaseManager.create(ReleaseModel("uuid", "ABBA", "ABBA", "1975", 1, true, true))
        release = ReleaseManager.findByTitle("ABBA")!!
        Timber.Forest.i(release.toString())
        if (release.id.isNotEmpty()) {
            ReleaseManager.createTrack(release, TrackModel("uuid", 1, 1, "Mamma Mia", "ABBA"))
            ReleaseManager.createTrack(release, TrackModel("uuid", 1, 2, "Hey, Hey Helen", "ABBA"))
            ReleaseManager.createTrack(release, TrackModel("uuid", 1, 3, "Tropical Loveland", "ABBA"))
            ReleaseManager.createTrack(release, TrackModel("uuid", 1, 4, "SOS", "ABBA"))
            ReleaseManager.createTrack(release, TrackModel("uuid", 1, 5, "Man In The Middle", "ABBA"))
            ReleaseManager.createTrack(release, TrackModel("uuid", 1, 6, "Bang-A-Boomerang", "ABBA"))
            ReleaseManager.createTrack(release, TrackModel("uuid", 1, 7, "I Do, I Do, I Do, I Do, I Do", "ABBA"))
            ReleaseManager.createTrack(release, TrackModel("uuid", 1, 8, "Rock Me", "ABBA"))
            ReleaseManager.createTrack(release, TrackModel("uuid", 1, 9, "Intermezzo No. 1", "ABBA"))
            ReleaseManager.createTrack(release, TrackModel("uuid", 1, 10, "I've Been Waiting For You", "ABBA"))
            ReleaseManager.createTrack(release, TrackModel("uuid", 1, 11, "So Long", "ABBA"))
        }

        // Arrival
        ReleaseManager.create(ReleaseModel("uuid", "Arrival", "ABBA", "1976", 1, true, true))
        release = ReleaseManager.findByTitle("Arrival")!!
        Timber.Forest.i(release.toString())
        if (release.id.isNotEmpty()) {
            ReleaseManager.createTrack(release, TrackModel("uuid", 1, 1, "When I Kissed The Teacher", "ABBA"))
            ReleaseManager.createTrack(release, TrackModel("uuid", 1, 2, "Dancing Queen", "ABBA"))
            ReleaseManager.createTrack(release, TrackModel("uuid", 1, 3, "My Love, My Life", "ABBA"))
            ReleaseManager.createTrack(release, TrackModel("uuid", 1, 4, "Dum Dum Diddle", "ABBA"))
            ReleaseManager.createTrack(release, TrackModel("uuid", 1, 5, "Knowing Me, Knowing You", "ABBA"))
            ReleaseManager.createTrack(release, TrackModel("uuid", 1, 6, "Money, Money, Money", "ABBA"))
            ReleaseManager.createTrack(release, TrackModel("uuid", 1, 7, "That's Me", "ABBA"))
            ReleaseManager.createTrack(release, TrackModel("uuid", 1, 8, "Why Did It Have To Be Me", "ABBA"))
            ReleaseManager.createTrack(release, TrackModel("uuid", 1, 9, "Tiger", "ABBA"))
            ReleaseManager.createTrack(release, TrackModel("uuid", 1, 10, "Arrival", "ABBA"))
        }

        // The Album
        ReleaseManager.create(ReleaseModel("uuid", "The Album", "ABBA", "1977", 1, true, true))
        release = ReleaseManager.findByTitle("The Album")!!
        Timber.Forest.i(release.toString())
        if (release.id.isNotEmpty()) {
            ReleaseManager.createTrack(release, TrackModel("uuid", 1, 1, "Eagle", "ABBA"))
            ReleaseManager.createTrack(release, TrackModel("uuid", 1, 2, "Take A Chance On Me", "ABBA"))
            ReleaseManager.createTrack(release, TrackModel("uuid", 1, 3, "One Man, One Woman", "ABBA"))
            ReleaseManager.createTrack(release, TrackModel("uuid", 1, 4, "The Name Of The Game", "ABBA"))
            ReleaseManager.createTrack(release, TrackModel("uuid", 1, 5, "Move On", "ABBA"))
            ReleaseManager.createTrack(release, TrackModel("uuid", 1, 6, "Hole In Your Soul", "ABBA"))
            ReleaseManager.createTrack(release, TrackModel("uuid", 1, 7, "Thank You For The Music", "ABBA"))
            ReleaseManager.createTrack(release, TrackModel("uuid", 1, 8, "I Wonder (Departure)", "ABBA"))
            ReleaseManager.createTrack(release, TrackModel("uuid", 1, 9, "I'm A Marionette", "ABBA"))
        }

        // Voulez-Vous
        ReleaseManager.create(ReleaseModel("uuid", "Voulez-Vous", "ABBA", "1979", 1, true, true))
        release = ReleaseManager.findByTitle("Voulez-Vous")!!
        Timber.Forest.i(release.toString())
        if (release.id.isNotEmpty()) {
            ReleaseManager.createTrack(release, TrackModel("uuid", 1, 1, "As Good As New", "ABBA"))
            ReleaseManager.createTrack(release, TrackModel("uuid", 1, 2, "Voulez-Vous", "ABBA"))
            ReleaseManager.createTrack(release, TrackModel("uuid", 1, 3, "I Have A Dream", "ABBA"))
            ReleaseManager.createTrack(release, TrackModel("uuid", 1, 4, "Angeleyes", "ABBA"))
            ReleaseManager.createTrack(release, TrackModel("uuid", 1, 5, "The King Has Lost His Crown", "ABBA"))
            ReleaseManager.createTrack(release, TrackModel("uuid", 1, 6, "Does Your Mother Know", "ABBA"))
            ReleaseManager.createTrack(release, TrackModel("uuid", 1, 7, "If It Wasn't For The Nights", "ABBA"))
            ReleaseManager.createTrack(release, TrackModel("uuid", 1, 8, "Chiquitita", "ABBA"))
            ReleaseManager.createTrack(release, TrackModel("uuid", 1, 9, "Lovers (Live A Little Longer)", "ABBA"))
            ReleaseManager.createTrack(release, TrackModel("uuid", 1, 10, "Kisses Of Fire", "ABBA"))
        }

        // Super Trouper
        ReleaseManager.create(ReleaseModel("uuid", "Super Trouper", "ABBA", "1980", 1, true, true))
        release = ReleaseManager.findByTitle("Super Trouper")!!
        Timber.Forest.i(release.toString())
        if (release.id.isNotEmpty()) {
            ReleaseManager.createTrack(release, TrackModel("uuid", 1, 1, "Super Trouper", "ABBA"))
            ReleaseManager.createTrack(release, TrackModel("uuid", 1, 2, "The Winner Takes It All", "ABBA"))
            ReleaseManager.createTrack(release, TrackModel("uuid", 1, 3, "On And On And On", "ABBA"))
            ReleaseManager.createTrack(release, TrackModel("uuid", 1, 4, "Andante, Andante", "ABBA"))
            ReleaseManager.createTrack(release, TrackModel("uuid", 1, 5, "Me And I", "ABBA"))
            ReleaseManager.createTrack(release, TrackModel("uuid", 1, 6, "Happy New Year", "ABBA"))
            ReleaseManager.createTrack(release, TrackModel("uuid", 1, 7, "Our Last Summer", "ABBA"))
            ReleaseManager.createTrack(release, TrackModel("uuid", 1, 8, "The Piper", "ABBA"))
            ReleaseManager.createTrack(release, TrackModel("uuid", 1, 9, "Lay All Your Love On Me", "ABBA"))
            ReleaseManager.createTrack(release, TrackModel("uuid", 1, 10, "The Way Old Friends Do (live)", "ABBA"))
        }

        // The Visitors
        ReleaseManager.create(ReleaseModel("uuid", "The Visitors", "ABBA", "1981", 1, true, true))
        release = ReleaseManager.findByTitle("The Visitors")!!
        Timber.Forest.i(release.toString())
        if (release.id.isNotEmpty()) {
            ReleaseManager.createTrack(release, TrackModel("uuid", 1, 1, "The Visitors", "ABBA"))
            ReleaseManager.createTrack(release, TrackModel("uuid", 1, 2, "Head Over Heels", "ABBA"))
            ReleaseManager.createTrack(release, TrackModel("uuid", 1, 3, "When All Is Said And Done", "ABBA"))
            ReleaseManager.createTrack(release, TrackModel("uuid", 1, 4, "Soldiers", "ABBA"))
            ReleaseManager.createTrack(release, TrackModel("uuid", 1, 5, "I Let The Music Speak", "ABBA"))
            ReleaseManager.createTrack(release, TrackModel("uuid", 1, 6, "One Of Us", "ABBA"))
            ReleaseManager.createTrack(release, TrackModel("uuid", 1, 7, "Two For The Price Of One", "ABBA"))
            ReleaseManager.createTrack(release, TrackModel("uuid", 1, 8, "Slipping Through My Fingers", "ABBA"))
            ReleaseManager.createTrack(release, TrackModel("uuid", 1, 9, "Like An Angel Passing Through My Room", "ABBA"))
        }

        // Voyage
        ReleaseManager.create(ReleaseModel("uuid", "Voyage", "ABBA", "2021", 1, true, true))
        release = ReleaseManager.findByTitle("Voyage")!!
        Timber.Forest.i(release.toString())
        if (release.id.isNotEmpty()) {
            ReleaseManager.createTrack(release, TrackModel("uuid", 1, 1, "I Still Have Faith In You", "ABBA"))
            ReleaseManager.createTrack(release, TrackModel("uuid", 1, 2, "When You Danced With Me", "ABBA"))
            ReleaseManager.createTrack(release, TrackModel("uuid", 1, 3, "Little Things", "ABBA"))
            ReleaseManager.createTrack(release, TrackModel("uuid", 1, 4, "Don't Shut Me Down", "ABBA"))
            ReleaseManager.createTrack(release, TrackModel("uuid", 1, 5, "Just A Notion", "ABBA"))
            ReleaseManager.createTrack(release, TrackModel("uuid", 1, 6, "I Can Be That Woman", "ABBA"))
            ReleaseManager.createTrack(release, TrackModel("uuid", 1, 7, "Keep An Eye On Dan", "ABBA"))
            ReleaseManager.createTrack(release, TrackModel("uuid", 1, 8, "Bumblebee", "ABBA"))
            ReleaseManager.createTrack(release, TrackModel("uuid", 1, 9, "No Doubt About It", "ABBA"))
            ReleaseManager.createTrack(release, TrackModel("uuid", 1, 10, "Ode To Freedom", "ABBA"))
        }
    }
}