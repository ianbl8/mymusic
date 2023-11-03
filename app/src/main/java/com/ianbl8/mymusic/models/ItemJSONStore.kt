package com.ianbl8.mymusic.models

import android.content.Context
import android.net.Uri
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import com.ianbl8.mymusic.helpers.*
import timber.log.Timber.Forest.i
import java.lang.reflect.Type

const val JSON_FILE = "items.json"
val gsonBuilder: Gson =
    GsonBuilder().setPrettyPrinting().registerTypeAdapter(Uri::class.java, UriParser()).create()
val listType: Type = object : TypeToken<ArrayList<ItemModel>>() {}.type

class ItemJSONStore(private val context: Context) : ItemStore {

    var items = mutableListOf<ItemModel>()

    init {
        if (exists(context, JSON_FILE)) {
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
        val jsonString = gsonBuilder.toJson(items, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        items = gsonBuilder.fromJson(jsonString, listType)
    }

    fun logAll() {
        items.forEach {
            i("${it}")
        }
    }
}

class UriParser : JsonDeserializer<Uri>, JsonSerializer<Uri> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Uri {
        return Uri.parse(json?.asString)
    }

    override fun serialize(
        src: Uri?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src.toString())
    }
}