package com.ianbl8.mymusic.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import com.ianbl8.mymusic.R
import com.ianbl8.mymusic.databinding.ActivityTrackBinding
import com.ianbl8.mymusic.main.MainApp
import com.ianbl8.mymusic.models.ReleaseModel
import com.ianbl8.mymusic.models.TrackModel
import timber.log.Timber.Forest.i

class TrackActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTrackBinding
    var track = TrackModel()
    var release = ReleaseModel()
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTrackBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = this.resources.getString(R.string.menu_add_track)
        setSupportActionBar(binding.toolbar)
        i("TrackActivity started")

        var edit = false

        @Suppress("DEPRECATION")
        if (intent.hasExtra("track_edit")) {
            edit = true
            release = intent.extras?.getParcelable("track_edit")!!
            track = intent.extras?.getParcelable("track_to_edit")!!
            i("Edit ${release}")
            i("Edit ${track}")
            binding.etDiscNumber.setText(track.discNumber.toString())
            binding.etTrackNumber.setText(track.trackNumber.toString())
            binding.etTrackTitle.setText(track.trackTitle)
            binding.etTrackArtist.setText(track.trackArtist)
            binding.toolbar.title = "edit ${track.trackTitle}"
            binding.btnAddTrack.setText(R.string.btn_edit_track)
            binding.btnDeleteTrack.isEnabled = true
        } else if (intent.hasExtra("track_add")) {
            release = intent.extras?.getParcelable("track_add")!!
            i("Add to ${release}")
        } else {
            finish()
        }

        app = application as MainApp

        binding.btnAddTrack.setOnClickListener {
            i("btnAddTrack pressed")
            track.discNumber = binding.etDiscNumber.text.toString().toInt()
            track.trackNumber = binding.etTrackNumber.text.toString().toInt()
            track.trackTitle = binding.etTrackTitle.text.toString()
            track.trackArtist = binding.etTrackArtist.text.toString()
            if (track.trackArtist.isEmpty()) {
                track.trackArtist = release.artist
            }
            if (track.trackTitle.isNotEmpty()) {
                if (edit) {
                    i("Update track: ${track.id}")
                    app.releases.updateTrack(release, track.copy())
                } else {
                    i("Add track: ${track.trackTitle}")
                    // app.tracks.create(track.copy())
                    app.releases.createTrack(release, track.copy())
                }
                setResult(RESULT_OK)
                finish()
            } else {
                Snackbar.make(it, "Enter valid track details", Snackbar.LENGTH_LONG)
                    .show()
                i("Invalid track")
            }
        }

        binding.btnDeleteTrack.setOnClickListener {
            i("btnDeleteTrack pressed")
            setResult(99)
            app.releases.deleteTrack(release, track)
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
