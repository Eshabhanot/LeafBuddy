package com.example.plantcarereminder

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ManagePlantsActivity : AppCompatActivity() {

    private lateinit var plantDao: PlantDao
    private lateinit var adapter: PlantAdapter
    private val plantList = mutableListOf<Plant>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_plant_list)

        // 🔥 Toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "My Plants 🌱"

        val db = AppDatabase.getDatabase(this)
        plantDao = db.plantDao()

        val etPlantName = findViewById<EditText>(R.id.etPlantName)
        val etFrequency = findViewById<EditText>(R.id.etFrequency)
        val btnAdd = findViewById<Button>(R.id.btnAdd)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        recyclerView.layoutManager = LinearLayoutManager(this)

        // ✅ FIXED: pass DAO + refresh function
        adapter = PlantAdapter(this, plantList, plantDao) {
            loadPlants()
        }
        recyclerView.adapter = adapter

        loadPlants()

        // ➕ Add Plant
        btnAdd.setOnClickListener {
            val name = etPlantName.text.toString().trim()
            val freq = etFrequency.text.toString().trim()

            if (name.isEmpty() || freq.isEmpty()) {
                Toast.makeText(this, "Fill all fields ❌", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch(Dispatchers.IO) {
                plantDao.insertPlant(
                    Plant(name = name, frequency = freq, imageUrl = "")
                )

                withContext(Dispatchers.Main) {
                    Toast.makeText(this@ManagePlantsActivity, "Plant Added 🌱", Toast.LENGTH_SHORT).show()
                    etPlantName.text.clear()
                    etFrequency.text.clear()
                    loadPlants()
                }
            }
        }
    }

    // 💾 Load plants
    private fun loadPlants() {
        lifecycleScope.launch(Dispatchers.IO) {
            val localPlants = plantDao.getAllPlants() ?: emptyList()

            withContext(Dispatchers.Main) {
                plantList.clear()
                plantList.addAll(localPlants.reversed())
                adapter.notifyDataSetChanged()
            }
        }
    }

    // 🔥 Menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.logout) {
            val sharedPref = getSharedPreferences("UserSession", MODE_PRIVATE)
            sharedPref.edit().clear().apply()

            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
        return true
    }
}
