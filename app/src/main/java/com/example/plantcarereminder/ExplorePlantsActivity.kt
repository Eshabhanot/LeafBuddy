
package com.example.plantcarereminder

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.plantcarereminder.api.RetrofitInstance
import com.example.plantcarereminder.api.PlantData
import kotlinx.coroutines.launch

class ExplorePlantsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private val plantList = mutableListOf<PlantData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_explore_plants)

        recyclerView = findViewById(R.id.recyclerViewExplore)
        recyclerView.layoutManager = LinearLayoutManager(this)

        fetchPlants()
    }

    private fun fetchPlants() {
        lifecycleScope.launch {
            try {
                val response = RetrofitInstance.api.getPlants("sk-HOga69e7421fe731516442")

                plantList.clear()
                plantList.addAll(response.data)

                recyclerView.adapter = ExplorePlantAdapter(plantList)

            } catch (e: Exception) {
                Toast.makeText(
                    this@ExplorePlantsActivity,
                    "Error loading plants",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
