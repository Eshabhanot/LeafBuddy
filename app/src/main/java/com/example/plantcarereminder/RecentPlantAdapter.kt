package com.example.plantcarereminder

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecentPlantAdapter(
    private val context: Context,
    private val plantList: List<Plant>
) : RecyclerView.Adapter<RecentPlantAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.tvPlantName)
        val freq: TextView = itemView.findViewById(R.id.tvPlantFreq)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_recent_plant, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val plant = plantList[position]
        holder.name.text = plant.name
        holder.freq.text = "Water every ${plant.frequency}"
    }

    override fun getItemCount(): Int = plantList.size
}