package com.ianbl8.mymusic.models

import android.os.Parcelable
import com.google.firebase.database.Exclude
import kotlinx.parcelize.Parcelize

@Parcelize
data class TrackModel(
    var uid: String = "",
    var discNumber: Int = 0,
    var trackNumber: Int = 0,
    var trackTitle: String = "",
    var trackArtist: String = "",
) : Parcelable {
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "uid" to uid,
            "discNumber" to discNumber,
            "trackNumber" to trackNumber,
            "trackTitle" to trackTitle,
            "trackArtist" to trackArtist
        )
    }
}