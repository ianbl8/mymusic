package com.ianbl8.mymusic.models

import java.util.UUID

interface ItemStore {
    fun findAll(): List<ItemModel>
    fun create(item: ItemModel)
    fun update(item: ItemModel)
    fun generateId(): String {
        return UUID.randomUUID().toString()
    }
}