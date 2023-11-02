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
import com.ianbl8.mymusic.adapters.ItemAdapter
import com.ianbl8.mymusic.databinding.ActivityListBinding
import com.ianbl8.mymusic.main.MainApp
import timber.log.Timber.Forest.i

class ListActivity : AppCompatActivity() {

    lateinit var app: MainApp
    private lateinit var binding: ActivityListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = this.resources.getString(R.string.menu_list)
        setSupportActionBar(binding.toolbar)
        i("ListActivity started")

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = ItemAdapter(app.items.findAll())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_list, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.add_item -> {
                val launcherIntent = Intent(this, ItemActivity::class.java)
                getResult.launch(launcherIntent)
            }
        }
        return super.onOptionsItemSelected(menuItem)
    }

    private val getResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            (binding.recyclerView.adapter)?.notifyItemRangeChanged(0, app.items.findAll().size)
        }
    }
}

