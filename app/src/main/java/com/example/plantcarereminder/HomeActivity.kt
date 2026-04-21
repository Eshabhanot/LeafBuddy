package com.example.plantcarereminder

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeActivity : AppCompatActivity() {

    private lateinit var plantDao: PlantDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val db = AppDatabase.getDatabase(this)
        plantDao = db.plantDao()

        val tvPlantCount = findViewById<TextView>(R.id.tvPlantCount)
        val tvReminder = findViewById<TextView>(R.id.tvReminder)
        val tvWeather = findViewById<TextView>(R.id.tvWeather)

        val btnAddPlant = findViewById<Button>(R.id.btnAddPlant)
        val btnViewPlants = findViewById<Button>(R.id.btnViewPlants)

        // 🌱 Load Plant Count + Reminder
        lifecycleScope.launch(Dispatchers.IO) {
            val plants = plantDao.getAllPlants() ?: emptyList()

            withContext(Dispatchers.Main) {
                tvPlantCount.text = "Total Plants: ${plants.size}"

                tvReminder.text = if (plants.isNotEmpty()) {
                    "Reminder: Water your plants today 💧"
                } else {
                    "No plants added yet"
                }
            }
        }

        // 🌤️ REAL WEATHER API (Open-Meteo - current_weather)
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = WeatherRetrofit.api.getWeather(
                    lat = 28.6,   // Delhi
                    lon = 77.2
                )

                val temp = response.current_weather.temperature

                val message = when {
                    temp > 35 -> "🔥 Hot! Water more 💧"
                    temp < 15 -> "❄️ Cold! Water less"
                    else -> "🌿 Perfect weather"
                }

                withContext(Dispatchers.Main) {
                    tvWeather.text = "🌤️ $temp°C - $message"
                }

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    tvWeather.text = "Weather unavailable ❌"
                }
            }
        }

        // ➕ Add Plant
        btnAddPlant.setOnClickListener {
            startActivity(Intent(this, ManagePlantsActivity::class.java))
        }

        // 📋 View Plants
        btnViewPlants.setOnClickListener {
            startActivity(Intent(this, ExplorePlantsActivity::class.java))
        }
    }
}