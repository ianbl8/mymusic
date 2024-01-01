package com.ianbl8.mymusic.models

import android.net.Uri
import android.os.Parcelable
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.parcelize.Parcelize

@IgnoreExtraProperties
@Parcelize
data class ReleaseModel(
    var uid: String? = "",
    var title: String = "",
    var artist: String = "",
    var year: String = "",
    var discs: Int = 0,
    var physical: Boolean = false,
    var digital: Boolean = false,
    var cover: String = "",
    var tracks: MutableList<TrackModel> = mutableListOf(),
    var email: String? = "example@example.com",
) : Parcelable
{
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "uid" to uid,
            "title" to title,
            "artist" to artist,
            "year" to year,
            "discs" to discs,
            "physical" to physical,
            "digital" to digital,
            "cover" to cover,
            "tracks" to tracks,
            "email" to email
        )
    }
}
