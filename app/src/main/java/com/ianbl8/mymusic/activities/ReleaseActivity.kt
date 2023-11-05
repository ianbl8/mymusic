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
import com.ianbl8.mymusic.databinding.ActivityReleaseBinding
import com.ianbl8.mymusic.helpers.showImagePicker
import com.ianbl8.mymusic.main.MainApp
import com.ianbl8.mymusic.models.ReleaseModel
import com.squareup.picasso.Picasso
import timber.log.Timber.Forest.i
import java.util.Calendar

class ReleaseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReleaseBinding
    private lateinit var imageIntentLauncher: ActivityResultLauncher<Intent>
    var release = ReleaseModel()
    lateinit var app: MainApp
    val thisYear = Calendar.getInstance().get(Calendar.YEAR)
    val nextYear = thisYear + 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReleaseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = this.resources.getString(R.string.menu_release)
        setSupportActionBar(binding.toolbar)
        i("ReleaseActivity started")

        var edit = false

        if (intent.hasExtra("release_edit")) {
            edit = true
            @Suppress("DEPRECATION")
            release = intent.extras?.getParcelable("release_edit")!!
            binding.etTitle.setText(release.title)
            binding.etArtist.setText(release.artist)
            binding.etYear.setText(release.year)
            binding.etDiscs.setText(release.discs.toString())
            binding.cbPhysical.isChecked = release.physical
            binding.cbDigital.isChecked = release.digital
            if (release.cover.toString().isNotEmpty()) {
                Picasso.get().load(release.cover).into(binding.ivCover)
            }
            binding.toolbar.title = "edit ${release.title}"
            binding.btnAddRelease.setText(R.string.btn_edit_release)
            binding.btnDeleteRelease.isEnabled = true
        }

        app = application as MainApp

        binding.btnAddRelease.setOnClickListener {
            i("btnAddRelease pressed")
            release.title = binding.etTitle.text.toString()
            release.artist = binding.etArtist.text.toString()
            release.year = binding.etYear.text.toString()
            val discs = binding.etDiscs.text.toString()
            if (discs.isNotEmpty()) {
                release.discs = discs.toInt()
            } else {
                release.discs = 1
            }
            release.physical = binding.cbPhysical.isChecked
            release.digital = binding.cbDigital.isChecked
            if (release.title.isNotEmpty() && release.artist.isNotEmpty() && release.year.isNotEmpty()) {
                if (release.year.toLong() < 1900 || release.year.toLong() > nextYear) {
                    Snackbar.make(
                        it,
                        "Choose a valid year between 1900 and $nextYear",
                        Snackbar.LENGTH_LONG
                    ).show()
                    i("Invalid year")
                } else {
                    if (edit) {
                        i("Update release: ${release.id}")
                        app.releases.update(release.copy())
                    } else {
                        i("Create release: ${release.id}")
                        app.releases.create(release.copy())
                    }
                    setResult(RESULT_OK)
                    finish()
                }
            } else {
                Snackbar.make(it, "Enter a valid title, artist and year", Snackbar.LENGTH_LONG)
                    .show()
                i("Invalid release")
            }
        }

        binding.btnAddCover.setOnClickListener {
            showImagePicker(imageIntentLauncher, this)
        }

        registerImagePickerCallback()

        binding.btnTracks.setOnClickListener {
            startActivity(Intent(this@ReleaseActivity, TrackListActivity::class.java))
        }

        binding.btnDeleteRelease.setOnClickListener {
            i("btnDeleteRelease pressed")
            setResult(99)
            app.releases.delete(release)
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_release, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.cancel_release -> {
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
                            val image = result.data!!.data!!
                            contentResolver.takePersistableUriPermission(
                                image,
                                Intent.FLAG_GRANT_READ_URI_PERMISSION
                            )
                            release.cover = image
                            Picasso.get().load(release.cover).into(binding.ivCover)
                        }
                    }

                    RESULT_CANCELED -> {}
                    else -> {}
                }
            }
    }
}