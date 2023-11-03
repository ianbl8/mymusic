package com.ianbl8.mymusic.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
import com.ianbl8.mymusic.R
import com.ianbl8.mymusic.databinding.ActivityItemBinding
import com.ianbl8.mymusic.helpers.showImagePicker
import com.ianbl8.mymusic.main.MainApp
import com.ianbl8.mymusic.models.ItemModel
import com.squareup.picasso.Picasso
import timber.log.Timber.Forest.i
import java.util.Calendar
import java.util.UUID

class ItemActivity : AppCompatActivity() {

    private lateinit var binding: ActivityItemBinding
    private lateinit var imageIntentLauncher: ActivityResultLauncher<Intent>
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

        var edit = false

        if (intent.hasExtra("item_edit")) {
            edit = true
            @Suppress("DEPRECATION")
            item = intent.extras?.getParcelable("item_edit")!!
            binding.etTitle.setText(item.title)
            binding.etArtist.setText(item.artist)
            binding.etYear.setText(item.year)
            if (item.physical) {
                binding.cbPhysical.setChecked(true)
            } else {
                binding.cbPhysical.setChecked(false)
            }
            if (item.digital) {
                binding.cbDigital.setChecked(true)
            } else {
                binding.cbDigital.setChecked(false)
            }
            if (item.cover.toString().isNotEmpty()) {
                Picasso.get().load(item.cover).into(binding.ivCover)
            }
            binding.toolbar.title = "edit ${item.title}"
        }

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
                    Snackbar.make(
                        it,
                        "Choose a valid year between 1900 and $nextYear",
                        Snackbar.LENGTH_LONG
                    ).show()
                    i("Invalid year")
                } else {
                    if (edit) {
                        i("Update item: ${item.id}")
                        app.items.update(item.copy())
                    } else {
                        item.id = UUID.randomUUID().toString()
                        i("Create item: ${item.id}")
                        app.items.create(item.copy())
                    }
                    setResult(RESULT_OK)
                    finish()
                }
            } else {
                Snackbar.make(it, "Enter a valid title, artist and year", Snackbar.LENGTH_LONG)
                    .show()
                i("Invalid item")
            }
        }

        binding.btnAddCover.setOnClickListener() {
            showImagePicker(imageIntentLauncher)
        }

        registerImagePickerCallback()
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

    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                when (result.resultCode) {
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got result ${result.data!!.data}")
                            item.cover = result.data!!.data!!
                            Picasso.get().load(item.cover).into(binding.ivCover)
                        }
                    }
                    RESULT_CANCELED -> {}
                    else -> {}
                }
            }
    }
}