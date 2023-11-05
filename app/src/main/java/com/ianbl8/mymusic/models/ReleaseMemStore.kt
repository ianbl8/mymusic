package com.ianbl8.mymusic.models

import timber.log.Timber.Forest.i

class ReleaseMemStore: ReleaseStore {
    val releases = ArrayList<ReleaseModel>()

    override fun findAll(): List<ReleaseModel> {
        return releases
    }

    override fun create(release: ReleaseModel) {
        release.id = generateId()
        releases.add(release)
        logAll()
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
            logAll()
        }
    }

    override fun delete(release: ReleaseModel) {
        releases.remove(release)
    }

    fun logAll() {
        releases.forEach {
            i("${it}")
        }
    }
}