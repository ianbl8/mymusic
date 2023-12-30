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

    override fun findById(searchId: String): ReleaseModel? {
        val foundRelease: ReleaseModel? = releases.find { r -> r.id == searchId }
        return foundRelease
    }

    override fun findByTitle(searchTitle: String): ReleaseModel? {
        val foundRelease: ReleaseModel? = releases.find { r -> r.title == searchTitle }
        return foundRelease
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
            updateRelease.tracks = release.tracks
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

    override fun findTrack(releaseId: String, trackId: String): TrackModel? {
        val foundRelease: ReleaseModel? = releases.find { r -> r.id == releaseId }
        val foundTrack: TrackModel? = foundRelease?.tracks!!.find { t -> t.id == trackId }
        return foundTrack
    }

    override fun createTrack(release: ReleaseModel, track: TrackModel) {
        val updateRelease: ReleaseModel? = releases.find { r -> r.id == release.id }
        if (updateRelease != null) {
            track.id = generateId()
            updateRelease.tracks.add(track)
            logAll()
            serialize()
        }
    }

    override fun updateTrack(release: ReleaseModel, track: TrackModel) {
        val updateRelease: ReleaseModel? = releases.find { r -> r.id == release.id }
        if (updateRelease != null) {
            val updateTrack: TrackModel? = updateRelease.tracks.find { t -> t.id == track.id }
            val index = updateRelease.tracks.indexOf(updateTrack)
            i("index = ${index}")
            updateRelease.tracks[index] = track
            logAll()
            serialize()
        }
    }

    override fun deleteTrack(release: ReleaseModel, track: TrackModel) {
        val updateRelease: ReleaseModel? = releases.find { r -> r.id == release.id }
        if (updateRelease != null) {
            updateRelease.tracks.remove(track)
            logAll()
            serialize()
        }
    }

    fun logAll() {
        releases.forEach {
            i("${it}")
        }
    }
}