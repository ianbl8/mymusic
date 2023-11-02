package com.ianbl8.mymusic.models

interface ItemStore {
    fun findAll(): List<ItemModel>
    fun create(item: ItemModel)
    fun update(item: ItemModel)
}