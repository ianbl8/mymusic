package com.ianbl8.mymusic.helpers

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import com.ianbl8.mymusic.R

fun showImagePicker(intentLauncher: ActivityResultLauncher<Intent>) {
    var chooseFile = Intent(Intent.ACTION_OPEN_DOCUMENT)
    chooseFile.type = "image/*"
    chooseFile = Intent.createChooser(chooseFile, R.string.select_cover.toString())
    intentLauncher.launch(chooseFile)
}