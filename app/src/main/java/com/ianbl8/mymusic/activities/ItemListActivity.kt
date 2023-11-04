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
import com.ianbl8.mymusic.adapters.ItemListener
import com.ianbl8.mymusic.databinding.ActivityItemListBinding
import com.ianbl8.mymusic.main.MainApp
import com.ianbl8.mymusic.models.ItemModel
import timber.log.Timber.Forest.i

class ItemListActivity : AppCompatActivity(), ItemListener {

    lateinit var app: MainApp
    private lateinit var binding: ActivityItemListBinding
    private var position: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = this.resources.getString(R.string.menu_list)
        setSupportActionBar(binding.toolbar)
        i("ListActivity started")

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = ItemAdapter(app.items.findAll(), this)
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

    override fun onItemClick(item: ItemModel, pos: Int) {
        val launcherIntent = Intent(this, ItemActivity::class.java)
        launcherIntent.putExtra("item_edit", item)
        position = pos
        getClickResult.launch(launcherIntent)
    }

    private val getClickResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            (binding.recyclerView.adapter)?.notifyItemRangeChanged(0, app.items.findAll().size)
        } else {
            if (it.resultCode == 99) {
                (binding.recyclerView.adapter)?.notifyItemRemoved(position)
            }
        }
    }
}

