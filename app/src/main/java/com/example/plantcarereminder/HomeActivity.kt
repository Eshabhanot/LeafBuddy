package com.example.plantcarereminder

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeActivity : AppCompatActivity() {

    private lateinit var plantDao: PlantDao
    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val db = AppDatabase.getDatabase(this)
        plantDao = db.plantDao()

        sharedPref = getSharedPreferences("UserSession", MODE_PRIVATE)

        // 👋 Views
        val tvWelcome = findViewById<TextView>(R.id.tvWelcome)
        val tvPlantCount = findViewById<TextView>(R.id.tvPlantCount)
        val tvReminder = findViewById<TextView>(R.id.tvReminder)
        val tvWeather = findViewById<TextView>(R.id.tvWeather)
        val tvCondition = findViewById<TextView>(R.id.tvCondition)
        val tvTip = findViewById<TextView>(R.id.tvTip)   // ✅ IMPORTANT

        val btnAddPlant = findViewById<Button>(R.id.btnAddPlant)
        val btnViewPlants = findViewById<Button>(R.id.btnViewPlants)

        // 🌟 Personalized Welcome
        val name = sharedPref.getString("username", "User")

        val hour = java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY)
        val greeting = when {
            hour < 12 -> "Good Morning"
            hour < 18 -> "Good Afternoon"
            else -> "Good Evening"
        }

        tvWelcome.text = "$greeting, $name 👋"

        // 🌱 Load Plants
        lifecycleScope.launch(Dispatchers.IO) {
            val plants = plantDao.getAllPlants() ?: emptyList()

            withContext(Dispatchers.Main) {
                tvPlantCount.text = "Total Plants: ${plants.size}"

                tvReminder.text = if (plants.isNotEmpty()) {
                    "Water your plants today 💧"
                } else {
                    "No plants added yet"
                }
            }
        }

        // 🌤️ Weather
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = WeatherRetrofit.api.getWeather(
                    lat = 28.6,
                    lon = 77.2
                )

                val temp = response.current_weather.temperature

                val message = when {
                    temp > 35 -> "Hot ☀️"
                    temp < 15 -> "Cold ❄️"
                    else -> "Pleasant 🌿"
                }

                withContext(Dispatchers.Main) {
                    tvWeather.text = "${temp.toInt()}°C"
                    tvCondition.text = message
                }

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    tvWeather.text = "--"
                    tvCondition.text = "Weather unavailable ❌"
                }
            }
        }

        // 🌿 Tip of the Day
        val tips = listOf(
            "Water plants early morning 💧",
            "Avoid overwatering 🌱",
            "Keep plants in sunlight ☀️",
            "Clean leaves weekly 🍃",
            "Use organic fertilizer 🌿",
            "Rotate plants for even growth 🔄"
        )

        tvTip.text = tips.random()

        // ➕ Buttons
        btnAddPlant.setOnClickListener {
            startActivity(Intent(this, ManagePlantsActivity::class.java))
        }

        btnViewPlants.setOnClickListener {
            startActivity(Intent(this, ExplorePlantsActivity::class.java))
        }
    }
}