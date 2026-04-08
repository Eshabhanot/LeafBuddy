package com.example.plantcarereminder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PlantAdapter(private val plantList: List<Plant>) :
    RecyclerView.Adapter<PlantAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.tvPlantName)
        val freq: TextView = view.findViewById(R.id.tvFrequency)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_plant, parent, false) // 🔥 custom card layout
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val plant = plantList[position]
        holder.name.text = plant.name
        holder.freq.text = "Water: ${plant.frequency}"
    }

    override fun getItemCount(): Int = plantList.size
}