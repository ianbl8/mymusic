package com.ianbl8.mymusic.models

import timber.log.Timber.Forest.i

class ItemMemStore: ItemStore {
    val items = ArrayList<ItemModel>()

    override fun findAll(): List<ItemModel> {
        return items
    }

    override fun create(item: ItemModel) {
        items.add(item)
        logAll()
    }

    fun logAll() {
        items.forEach {
            i("${it}")
        }
    }
}