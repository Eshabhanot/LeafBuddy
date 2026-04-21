package com.example.plantcarereminder

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class PlantDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plant_detail)

        val name = intent.getStringExtra("plant_name")
        val frequency = intent.getStringExtra("plant_frequency")
        val imageUrl = intent.getStringExtra("plant_image")

        val tvName = findViewById<TextView>(R.id.tvDetailName)
        val tvFreq = findViewById<TextView>(R.id.tvDetailFrequency)
        val img = findViewById<ImageView>(R.id.imgDetailPlant)

        tvName.text = name ?: "Unknown Plant"
        tvFreq.text = "Water: ${frequency ?: "N/A"}"

        if (!imageUrl.isNullOrEmpty()) {
            Glide.with(this)
                .load(imageUrl)
                .into(img)
        }
    }
}