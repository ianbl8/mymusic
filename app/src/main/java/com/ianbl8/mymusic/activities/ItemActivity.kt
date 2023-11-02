package com.ianbl8.mymusic.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import com.ianbl8.mymusic.R
import com.ianbl8.mymusic.databinding.ActivityItemBinding
import com.ianbl8.mymusic.main.MainApp
import com.ianbl8.mymusic.models.ItemModel
import timber.log.Timber.Forest.i
import java.util.Calendar
import java.util.UUID

class ItemActivity : AppCompatActivity() {

    private lateinit var binding: ActivityItemBinding
    var item = ItemModel()
    lateinit var app: MainApp
    val thisYear = Calendar.getInstance().get(Calendar.YEAR)
    val nextYear = thisYear + 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = this.resources.getString(R.string.menu_item)
        setSupportActionBar(binding.toolbar)
        i("ItemActivity started")

        app = application as MainApp

        binding.btnAddItem.setOnClickListener() {
            i("btnAddItem pressed")
            item.title = binding.etTitle.text.toString()
            item.artist = binding.etArtist.text.toString()
            item.year = binding.etYear.text.toString()
            item.physical = binding.cbPhysical.isChecked()
            item.digital = binding.cbDigital.isChecked()
            if (item.title.isNotEmpty() && item.artist.isNotEmpty() && item.year.isNotEmpty()) {
                if (item.year.toLong() < 1900 || item.year.toLong() > nextYear) {
                    Snackbar.make(it, "Choose a valid year between 1900 and $nextYear", Snackbar.LENGTH_LONG).show()
                    i("Invalid year")
                } else {
                    Snackbar.make(it, "\"${item.title}\" by ${item.artist} (${item.year}) added", Snackbar.LENGTH_SHORT).show()
                    item.id = UUID.randomUUID().toString()
                    app.items.add(item.copy())
                    i("Valid item: ${item.id}")
                    i("${item.title}, ${item.artist}, ${item.year}; P: ${item.physical}, D: ${item.digital}")
                    for (i in app.items.indices) {
                        i("${this.app.items[i]}")
                    }
                }
            } else {
                Snackbar.make(it, "Enter a valid title, artist and year", Snackbar.LENGTH_LONG).show()
                i("Invalid item")
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_item, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.cancel_item -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(menuItem)
    }
}