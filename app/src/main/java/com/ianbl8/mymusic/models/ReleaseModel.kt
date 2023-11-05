package com.ianbl8.mymusic.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ReleaseModel(
    var id: String = "",
    var title: String = "",
    var artist: String = "",
    var year: String = "",
    var discs: Int = 0,
    var physical: Boolean = false,
    var digital: Boolean = false,
    var cover: Uri = Uri.EMPTY
) : Parcelable
