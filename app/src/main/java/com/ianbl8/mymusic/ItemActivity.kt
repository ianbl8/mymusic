package com.ianbl8.mymusic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import com.ianbl8.mymusic.databinding.ActivityItemBinding
import timber.log.Timber.Forest.i
import java.util.Calendar

class ItemActivity : AppCompatActivity() {

    private lateinit var binding: ActivityItemBinding
    val thisYear = Calendar.getInstance().get(Calendar.YEAR)
    val nextYear = thisYear + 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
        i("ItemActivity started")

        binding.btnAddItem.setOnClickListener() {
            i("btnAddItem pressed")
            val itemTitle = binding.etTitle.text.toString()
            val itemArtist = binding.etArtist.text.toString()
            val itemYear = binding.etYear.text.toString()
            val itemPhysical = binding.cbPhysical.isChecked()
            val itemDigital = binding.cbDigital.isChecked()
            if (itemTitle.isNotEmpty() && itemArtist.isNotEmpty() && itemYear.isNotEmpty()) {
                if (itemYear.toLong() < 1900 || itemYear.toLong() > nextYear) {
                    i("Invalid year")
                    Snackbar.make(it, "Choose a valid year between 1900 and $nextYear", Snackbar.LENGTH_LONG).show()
                } else {
                    i("Valid item:")
                    i("$itemTitle, $itemArtist, $itemYear; P: $itemPhysical, D: $itemDigital")
                    Snackbar.make(it, "\"$itemTitle\" by $itemArtist ($itemYear) added", Snackbar.LENGTH_SHORT).show()
                }
            } else {
                i("Invalid item")
                Snackbar.make(it, "Enter a valid title, artist and year", Snackbar.LENGTH_LONG).show()
            }

        }
    }
}