package com.example.plantcarereminder

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var plantDao: PlantDao
    private lateinit var adapter: PlantAdapter
    private var plantList = mutableListOf<Plant>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = AppDatabase.getDatabase(this)
        plantDao = db.plantDao()

        val etPlantName = findViewById<EditText>(R.id.etPlantName)
        val etFrequency = findViewById<EditText>(R.id.etFrequency)
        val btnAdd = findViewById<Button>(R.id.btnAdd)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = PlantAdapter(plantList)
        recyclerView.adapter = adapter

        // 🔥 SAFE LOAD
        try {
            loadPlants()
        } catch (e: Exception) {
            Toast.makeText(this, "DB Error: ${e.message}", Toast.LENGTH_LONG).show()
        }

        btnAdd.setOnClickListener {

            val name = etPlantName.text.toString()
            val freq = etFrequency.text.toString()

            if (name.isEmpty() || freq.isEmpty()) {
                Toast.makeText(this, "Fill all fields ❌", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            try {
                val plant = Plant(name = name, frequency = freq)
                plantDao.insertPlant(plant)

                Toast.makeText(this, "Plant Added 🌱", Toast.LENGTH_SHORT).show()

                etPlantName.text.clear()
                etFrequency.text.clear()

                loadPlants()
            } catch (e: Exception) {
                Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun loadPlants() {
        plantList.clear()
        val data = plantDao.getAllPlants() ?: emptyList()
        plantList.addAll(data)
        adapter.notifyDataSetChanged()
    }
}