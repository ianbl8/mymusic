package com.ianbl8.mymusic.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.ianbl8.mymusic.R
import com.ianbl8.mymusic.adapters.TrackAdapter
import com.ianbl8.mymusic.adapters.TrackListener
import com.ianbl8.mymusic.databinding.ActivityTrackListBinding
import com.ianbl8.mymusic.main.MainApp
import com.ianbl8.mymusic.models.TrackModel
import timber.log.Timber.Forest.i

class TrackListActivity : AppCompatActivity(), TrackListener {

    lateinit var app: MainApp
    private lateinit var binding: ActivityTrackListBinding
    private var position: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTrackListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = this.resources.getString(R.string.menu_track_list)
        setSupportActionBar(binding.toolbar)
        i("TrackListActivity started")

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = TrackAdapter(app.tracks.findAll(), this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_track_list, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.add_track -> {
                val launcherIntent = Intent(this, TrackActivity::class.java)
                getResult.launch(launcherIntent)
            }
        }
        return super.onOptionsItemSelected(menuItem)
    }

    private val getResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            (binding.recyclerView.adapter)?.notifyItemRangeChanged(0, app.tracks.findAll().size)
        }
    }

    override fun onTrackClick(track: TrackModel, pos: Int) {
        val launcherIntent = Intent(this, TrackActivity::class.java)
        launcherIntent.putExtra("track_edit", track)
        position = pos
        getClickResult.launch(launcherIntent)
    }

    private val getClickResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            (binding.recyclerView.adapter)?.notifyItemRangeChanged(0, app.tracks.findAll().size)
        } else {
            if (it.resultCode == 99) {
                (binding.recyclerView.adapter)?.notifyItemRemoved(position)
            }
        }
    }
}