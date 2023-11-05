package com.ianbl8.mymusic.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.ianbl8.mymusic.R
import com.ianbl8.mymusic.main.MainApp
import timber.log.Timber.Forest.i

class WelcomeActivity : AppCompatActivity() {

    lateinit var app: MainApp
    private lateinit var btnSampleStart: Button
    private lateinit var btnStart: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        i("WelcomeActivity started")

        app = application as MainApp

        btnSampleStart = findViewById(R.id.btnSampleStart)
        btnSampleStart.setOnClickListener {
            app.sampleData()
            startActivity(Intent(this@WelcomeActivity, ReleaseListActivity::class.java))
        }

        btnStart = findViewById(R.id.btnStart)
        btnStart.setOnClickListener {
            startActivity(Intent(this@WelcomeActivity, ReleaseListActivity::class.java))
        }
    }
}