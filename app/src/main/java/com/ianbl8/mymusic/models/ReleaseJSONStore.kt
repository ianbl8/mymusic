package com.ianbl8.mymusic.models

import android.content.Context
import android.net.Uri
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import com.ianbl8.mymusic.helpers.*
import timber.log.Timber.Forest.i
import java.lang.reflect.Type

const val RELEASES_JSON_FILE = "releases.json"
val gsonBuilder: Gson =
    GsonBuilder().setPrettyPrinting().registerTypeAdapter(Uri::class.java, UriParser()).create()
val releasesListType: Type = object : TypeToken<ArrayList<ReleaseModel>>() {}.type

class ReleaseJSONStore(private val context: Context) : ReleaseStore {

    var releases = mutableListOf<ReleaseModel>()

    init {
        if (exists(context, RELEASES_JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): List<ReleaseModel> {
        logAll()
        return releases
    }

    override fun create(release: ReleaseModel) {
        release.id = generateId()
        releases.add(release)
        serialize()
    }

    override fun update(release: ReleaseModel) {
        val updateRelease: ReleaseModel? = releases.find { r -> r.id == release.id }
        if (updateRelease != null) {
            updateRelease.title = release.title
            updateRelease.artist = release.artist
            updateRelease.year = release.year
            updateRelease.discs = release.discs
            updateRelease.physical = release.physical
            updateRelease.digital = release.digital
            updateRelease.cover = release.cover
            serialize()
        }
    }

    override fun delete(release: ReleaseModel) {
        releases.remove(release)
        serialize()
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(releases, releasesListType)
        write(context, RELEASES_JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, RELEASES_JSON_FILE)
        releases = gsonBuilder.fromJson(jsonString, releasesListType)
    }

    fun logAll() {
        releases.forEach {
            i("${it}")
        }
    }
}