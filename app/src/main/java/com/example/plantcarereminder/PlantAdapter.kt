package com.example.plantcarereminder

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class PlantAdapter(
    private val context: Context,
    private val plantList: List<Plant>
) : RecyclerView.Adapter<PlantAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.tvPlantName)
        val freq: TextView = view.findViewById(R.id.tvFrequency)
        val image: ImageView = view.findViewById(R.id.imgPlant)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_plant, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val plant = plantList[position]

        holder.name.text = plant.name
        holder.freq.text = "Water: ${plant.frequency}"

        // ✅ Handle image
        if (plant.imageUrl.isNullOrEmpty()) {
            holder.image.visibility = View.GONE
        } else {
            holder.image.visibility = View.VISIBLE

            Glide.with(context)
                .load(plant.imageUrl)
                .into(holder.image)
        }

        // 🚀 CLICK LISTENER (NEW)
        holder.itemView.setOnClickListener {
            val intent = Intent(context, PlantDetailActivity::class.java)
            intent.putExtra("plant_name", plant.name)
            intent.putExtra("plant_frequency", plant.frequency)
            intent.putExtra("plant_image", plant.imageUrl)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = plantList.size
}