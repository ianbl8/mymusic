package com.ianbl8.mymusic

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class WelcomeActivity : AppCompatActivity() {

    private lateinit var btnStart: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        btnStart = findViewById(R.id.btnStart)
        btnStart.setOnClickListener {
            val intent = Intent(this@WelcomeActivity, ListActivity::class.java)
            startActivity(intent)
        }
    }
}