package com.ianbl8.mymusic.main

import android.app.Application
import com.ianbl8.mymusic.models.ItemModel
import timber.log.Timber
import timber.log.Timber.Forest.i

class MainApp: Application() {

    val items = ArrayList<ItemModel>()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("mymusic started")

        /* Items for testing
        items.add(ItemModel("Test0","Ring Ring", "ABBA", "1973", true, true))
        items.add(ItemModel("Test1","Waterloo", "ABBA", "1974", true, true))
        items.add(ItemModel("Test2","ABBA", "ABBA", "1975", true, true))
        items.add(ItemModel("Test3","Arrival", "ABBA", "1976", true, true))
        items.add(ItemModel("Test4","The Album", "ABBA", "1977", true, true))
        items.add(ItemModel("Test5","Voulez-Vous", "ABBA", "1979", true, true))
        items.add(ItemModel("Test6","Super Trouper", "ABBA", "1980", true, true))
        items.add(ItemModel("Test7","The Visitors", "ABBA", "1981", true, true))
        items.add(ItemModel("Test8","ABBA Live", "ABBA", "1986", true, true))
        items.add(ItemModel("Test9","Voyage", "ABBA", "2021", true, true))
         */
    }
}