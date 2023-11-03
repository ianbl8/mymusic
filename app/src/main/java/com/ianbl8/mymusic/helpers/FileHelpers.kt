package com.ianbl8.mymusic.helpers

import android.content.Context
import java.io.*
import java.lang.StringBuilder
import timber.log.Timber.Forest.e

fun write(context: Context, fileName: String, data: String) {
    try {
        val outputStreamWriter =
            OutputStreamWriter(context.openFileOutput(fileName, Context.MODE_PRIVATE))
        outputStreamWriter.write(data)
        outputStreamWriter.close()
    } catch (e: Exception) {
        e("Cannot read file: %s", e.toString())
    }
}

fun read(context: Context, fileName: String): String {
    var str = ""
    try {
        val inputStream = context.openFileInput(fileName)
        if (inputStream != null) {
            val inputStreamReader = InputStreamReader(inputStream)
            val bufferedReader = BufferedReader(inputStreamReader)
            val partialStr = StringBuilder()
            var done = false
            while (!done) {
                val line = bufferedReader.readLine()
                done = (line == null)
                if (line != null) partialStr.append(line)
            }
            inputStream.close()
            str = partialStr.toString()
        }
    } catch (e: FileNotFoundException) {
        e("File not found: %s", e.toString())
    } catch (e: IOException) {
        e("Cannot read file: %s", e.toString())
    }
    return str
}

fun exists(context: Context, fileName: String): Boolean {
    val file = context.getFileStreamPath(fileName)
    return file.exists()
}