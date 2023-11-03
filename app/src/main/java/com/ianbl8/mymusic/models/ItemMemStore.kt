package com.ianbl8.mymusic.models

import timber.log.Timber.Forest.i

class ItemMemStore: ItemStore {
    val items = ArrayList<ItemModel>()

    override fun findAll(): List<ItemModel> {
        return items
    }

    override fun create(item: ItemModel) {
        item.id = generateId()
        items.add(item)
        logAll()
    }

    override fun update(item: ItemModel) {
        val updateItem: ItemModel? = items.find { i -> i.id == item.id }
        if (updateItem != null) {
            updateItem.title = item.title
            updateItem.artist = item.artist
            updateItem.year = item.year
            updateItem.physical = item.physical
            updateItem.digital = item.digital
            updateItem.cover = item.cover
            logAll()
        }
    }

    override fun delete(item: ItemModel) {
        items.remove(item)
    }

    fun logAll() {
        items.forEach {
            i("${it}")
        }
    }
}