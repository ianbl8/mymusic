package com.ianbl8.mymusic.utils

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.activity.result.ActivityResultLauncher
import com.ianbl8.mymusic.R
import com.makeramen.roundedimageview.RoundedTransformationBuilder
import com.squareup.picasso.Transformation

fun showImagePicker(intentLauncher: ActivityResultLauncher<Intent>, context: Context) {
    var imagePickerTargetIntent = Intent()
    imagePickerTargetIntent.action = Intent.ACTION_OPEN_DOCUMENT
    imagePickerTargetIntent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
    imagePickerTargetIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    imagePickerTargetIntent.type = "image/*"
    imagePickerTargetIntent = Intent.createChooser(imagePickerTargetIntent, R.string.select_cover.toString())
    intentLauncher.launch(imagePickerTargetIntent)
}

fun customTransformation(): Transformation =
    RoundedTransformationBuilder()
        .borderColor(Color.WHITE)
        .borderWidthDp(2F)
        .cornerRadiusDp(35F)
        .oval(false)
        .build()