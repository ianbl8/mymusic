package com.ianbl8.mymusic.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TrackModel(
    var id: String = "",
    var discNumber: Int = 0,
    var trackNumber: Int = 0,
    var trackTitle: String = "",
    var trackArtist: String = "",
) : Parcelable