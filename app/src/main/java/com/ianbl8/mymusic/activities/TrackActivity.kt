package com.ianbl8.mymusic.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import com.ianbl8.mymusic.R
import com.ianbl8.mymusic.databinding.ActivityTrackBinding
import com.ianbl8.mymusic.main.MainApp
import com.ianbl8.mymusic.models.TrackModel
import timber.log.Timber.Forest.i

class TrackActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTrackBinding
    var track = TrackModel()
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTrackBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = "ADD TRACK"
        setSupportActionBar(binding.toolbar)
        i("TrackActivity started")

        var edit = false

        if (intent.hasExtra("track_edit")) {
            edit = true
            @Suppress("DEPRECATION")
            track = intent.extras?.getParcelable("track_edit")!!
            binding.etDiscNumber.setText(track.discNumber)
            binding.etTrackNumber.setText(track.trackNumber)
            binding.etTrackTitle.setText(track.trackTitle)
            binding.etTrackArtist.setText(track.trackArtist)
            binding.btnAddTrack.setText(R.string.btn_edit_track)
            binding.btnDeleteTrack.isEnabled = true
        }

        app = application as MainApp

        binding.btnAddTrack.setOnClickListener {
            i("btnAddTrack pressed")
            track.discNumber = binding.etDiscNumber.text.toString().toInt()
            track.trackNumber = binding.etTrackNumber.text.toString().toInt()
            track.trackTitle = binding.etTrackTitle.text.toString()
            track.trackArtist = binding.etTrackArtist.text.toString()
            // if trackArtist empty, use item.artist
            if (track.trackTitle.isNotEmpty()) {
                if (edit) {
                    i("Update item: ${track.id}")
                    app.tracks.update(track.copy())
                } else {
                    i("Create item: ${track.id}")
                    app.tracks.create(track.copy())
                }
                setResult(RESULT_OK)
                finish()
            } else {
                Snackbar.make(it, "Enter a valid title, artist and year", Snackbar.LENGTH_LONG)
                    .show()
                i("Invalid item")
            }
        }

        binding.btnDeleteTrack.setOnClickListener {
            i("btnDeleteTrack pressed")
            setResult(99)
            app.tracks.delete(track)
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_track, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.cancel_track -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(menuItem)
    }
}
