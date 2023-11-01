package com.ianbl8.mymusic.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.ianbl8.mymusic.R
import timber.log.Timber.Forest.i

class ListActivity : AppCompatActivity() {

    private lateinit var btnStart: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        i("ListActivity started")

        btnStart = findViewById(R.id.btnStart)
        btnStart.setOnClickListener {
            startActivity(Intent(this@ListActivity, ItemActivity::class.java))
        }
    }
}