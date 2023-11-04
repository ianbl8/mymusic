package com.ianbl8.mymusic.models

import java.util.UUID

interface TrackStore {
    fun findAll(): List<TrackModel>
    fun create(track: TrackModel)
    fun update(track: TrackModel)
    fun delete(track: TrackModel)
    fun generateId(): String {
        return UUID.randomUUID().toString()
    }
}