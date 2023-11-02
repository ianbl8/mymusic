package com.ianbl8.mymusic.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ItemModel(
    var id: String = "",
    var title: String = "",
    var artist: String = "",
    var year: String = "",
    var physical: Boolean = false,
    var digital: Boolean = false,
) : Parcelable
