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
import com.ianbl8.mymusic.adapters.ReleaseAdapter
import com.ianbl8.mymusic.adapters.ReleaseListener
import com.ianbl8.mymusic.databinding.ActivityReleaseListBinding
import com.ianbl8.mymusic.main.MainApp
import com.ianbl8.mymusic.models.ReleaseModel
import timber.log.Timber.Forest.i

class ReleaseListActivity : AppCompatActivity(), ReleaseListener {

    lateinit var app: MainApp
    private lateinit var binding: ActivityReleaseListBinding
    private var position: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReleaseListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = this.resources.getString(R.string.menu_list)
        setSupportActionBar(binding.toolbar)
        i("ReleaseListActivity started")

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = ReleaseAdapter(app.releases.findAll(), this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_release_list, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        /*
        when (menuItem.itemId) {
            R.id.add_release -> {
                val launcherIntent = Intent(this, ReleaseActivity::class.java)
                getResult.launch(launcherIntent)
            }
        }
         */
        return super.onOptionsItemSelected(menuItem)
    }

    private val getResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            (binding.recyclerView.adapter)?.notifyItemRangeChanged(0, app.releases.findAll().size)
        }
    }

    override fun onReleaseClick(release: ReleaseModel, pos: Int) {
        val launcherIntent = Intent(this, ReleaseActivity::class.java)
        launcherIntent.putExtra("release_edit", release)
        position = pos
        getClickResult.launch(launcherIntent)
    }

    private val getClickResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            (binding.recyclerView.adapter)?.notifyItemRangeChanged(0, app.releases.findAll().size)
        } else {
            if (it.resultCode == 99) {
                (binding.recyclerView.adapter)?.notifyItemRemoved(position)
            }
        }
    }
}

