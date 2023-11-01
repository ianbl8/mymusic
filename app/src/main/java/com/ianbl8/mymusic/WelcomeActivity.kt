package com.ianbl8.mymusic

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import timber.log.Timber
import timber.log.Timber.Forest.i

class WelcomeActivity : AppCompatActivity() {

    private lateinit var btnStart: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        Timber.plant(Timber.DebugTree())
        i("WelcomeActivity started")

        btnStart = findViewById(R.id.btnStart)
        btnStart.setOnClickListener {
            startActivity(Intent(this@WelcomeActivity, ListActivity::class.java))
        }
    }
}