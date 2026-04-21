package com.example.plantcarereminder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.plantcarereminder.api.PlantData

class ExplorePlantAdapter(
    private val list: List<PlantData>
) : RecyclerView.Adapter<ExplorePlantAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.tvName)
        val image: ImageView = view.findViewById(R.id.ivPlant)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_explore_plant, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val plant = list[position]

        holder.name.text = plant.common_name ?: "Unknown"

        Glide.with(holder.itemView.context)
            .load(plant.default_image?.regular_url)
            .into(holder.image)
    }
}
