package com.ianbl8.mymusic.models

import android.content.Context
import android.net.Uri
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import com.ianbl8.mymusic.helpers.*
import timber.log.Timber.Forest.i
import java.lang.reflect.Type

const val TRACKS_JSON_FILE = "tracks.json"
val tracksGsonBuilder: Gson =
    GsonBuilder().setPrettyPrinting().registerTypeAdapter(Uri::class.java, UriParser()).create()
val tracksListType: Type = object : TypeToken<ArrayList<TrackModel>>() {}.type

class TrackJSONStore(private val context: Context) : TrackStore {

    var tracks = mutableListOf<TrackModel>()

    init {
        if (exists(context, TRACKS_JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): List<TrackModel> {
        logAll()
        return tracks
    }

    override fun create(track: TrackModel) {
        track.id = generateId()
        tracks.add(track)
        serialize()
    }

    override fun update(track: TrackModel) {
        val updateTrack: TrackModel? = tracks.find { i -> i.id == track.id }
        if (updateTrack != null) {
            updateTrack.discNumber = track.discNumber
            updateTrack.trackNumber = track.trackNumber
            updateTrack.trackTitle = track.trackTitle
            updateTrack.trackArtist = track.trackArtist
            serialize()
        }
    }

    override fun delete(track: TrackModel) {
        tracks.remove(track)
        serialize()
    }

    private fun serialize() {
        val jsonString = tracksGsonBuilder.toJson(tracks, tracksListType)
        write(context, TRACKS_JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, TRACKS_JSON_FILE)
        tracks = tracksGsonBuilder.fromJson(jsonString, tracksListType)
    }

    fun logAll() {
        tracks.forEach {
            i("${it}")
        }
    }
}