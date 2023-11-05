package com.ianbl8.mymusic.models

import java.util.UUID

interface ReleaseStore {
    fun findAll(): List<ReleaseModel>
    fun create(release: ReleaseModel)
    fun update(release: ReleaseModel)
    fun delete(release: ReleaseModel)
    fun generateId(): String {
        return UUID.randomUUID().toString()
    }
}