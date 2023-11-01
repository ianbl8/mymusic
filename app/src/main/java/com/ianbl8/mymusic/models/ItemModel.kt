package com.ianbl8.mymusic.models

data class ItemModel(
    var id: String = "",
    var title: String = "",
    var artist: String = "",
    var year: String = "",
    var physical: Boolean = false,
    var digital: Boolean = false,
)
