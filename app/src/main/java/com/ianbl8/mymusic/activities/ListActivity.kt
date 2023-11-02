package com.ianbl8.mymusic.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ianbl8.mymusic.R
import com.ianbl8.mymusic.databinding.ActivityListBinding
import com.ianbl8.mymusic.databinding.CardItemBinding
import com.ianbl8.mymusic.main.MainApp
import com.ianbl8.mymusic.models.ItemModel
import timber.log.Timber.Forest.i

class ListActivity : AppCompatActivity() {

    lateinit var app: MainApp
    private lateinit var binding: ActivityListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)
        i("ListActivity started")

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = ItemAdapter(app.items)
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
            (binding.recyclerView.adapter)?.notifyItemRangeChanged(0, app.items.size)
        }
    }
}

class ItemAdapter constructor(private var items: List<ItemModel>) : RecyclerView.Adapter<ItemAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val item = items[holder.adapterPosition]
        holder.bind(item)
    }

    override fun getItemCount(): Int = items.size

    class MainHolder(private val binding: CardItemBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: ItemModel) {
            binding.itemTitle.text = item.title
            binding.itemArtist.text = item.artist
            binding.itemYear.text = "(${item.year})"
        }
    }
}