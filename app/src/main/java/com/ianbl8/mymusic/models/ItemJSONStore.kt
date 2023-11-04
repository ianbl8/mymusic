package com.ianbl8.mymusic.models

import android.content.Context
import android.net.Uri
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import com.ianbl8.mymusic.helpers.*
import timber.log.Timber.Forest.i
import java.lang.reflect.Type

const val ITEMS_JSON_FILE = "items.json"
val gsonBuilder: Gson =
    GsonBuilder().setPrettyPrinting().registerTypeAdapter(Uri::class.java, UriParser()).create()
val itemsListType: Type = object : TypeToken<ArrayList<ItemModel>>() {}.type

class ItemJSONStore(private val context: Context) : ItemStore {

    var items = mutableListOf<ItemModel>()

    init {
        if (exists(context, ITEMS_JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): List<ItemModel> {
        logAll()
        return items
    }

    override fun create(item: ItemModel) {
        item.id = generateId()
        items.add(item)
        serialize()
    }

    override fun update(item: ItemModel) {
        val updateItem: ItemModel? = items.find { i -> i.id == item.id }
        if (updateItem != null) {
            updateItem.title = item.title
            updateItem.artist = item.artist
            updateItem.year = item.year
            updateItem.discs = item.discs
            updateItem.physical = item.physical
            updateItem.digital = item.digital
            updateItem.cover = item.cover
            serialize()
        }
    }

    override fun delete(item: ItemModel) {
        items.remove(item)
        serialize()
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(items, itemsListType)
        write(context, ITEMS_JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, ITEMS_JSON_FILE)
        items = gsonBuilder.fromJson(jsonString, itemsListType)
    }

    fun logAll() {
        items.forEach {
            i("${it}")
        }
    }
}