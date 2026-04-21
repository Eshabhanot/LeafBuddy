package com.example.plantcarereminder

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlantAdapter(
    private val context: Context,
    private val plantList: List<Plant>,
    private val plantDao: PlantDao,
    private val refresh: () -> Unit
) : RecyclerView.Adapter<PlantAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.tvPlantName)
        val freq: TextView = view.findViewById(R.id.tvFrequency)
        val image: ImageView = view.findViewById(R.id.imgPlant)
        val btnEdit: Button = view.findViewById(R.id.btnEdit)
        val btnDelete: Button = view.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_plant, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = plantList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val plant = plantList[position]

        holder.name.text = plant.name
        holder.freq.text = "Water: ${plant.frequency}"

        // 🌿 Image handling
        if (plant.imageUrl.isNullOrEmpty()) {
            holder.image.visibility = View.GONE
        } else {
            holder.image.visibility = View.VISIBLE
            Glide.with(context)
                .load(plant.imageUrl)
                .into(holder.image)
        }

        // 🗑️ DELETE
        holder.btnDelete.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                plantDao.deletePlant(plant)
                (context as Activity).runOnUiThread {
                    Toast.makeText(context, "Deleted 🗑️", Toast.LENGTH_SHORT).show()
                    refresh()
                }
            }
        }

        // ✏️ EDIT
        holder.btnEdit.setOnClickListener {

            val etName = EditText(context)
            etName.setText(plant.name)

            val etFreq = EditText(context)
            etFreq.setText(plant.frequency)

            val layout = LinearLayout(context)
            layout.orientation = LinearLayout.VERTICAL
            layout.setPadding(20, 20, 20, 20)
            layout.addView(etName)
            layout.addView(etFreq)

            AlertDialog.Builder(context)
                .setTitle("Edit Plant")
                .setView(layout)
                .setPositiveButton("Update") { _, _ ->

                    val updatedPlant = plant.copy(
                        name = etName.text.toString(),
                        frequency = etFreq.text.toString()
                    )

                    CoroutineScope(Dispatchers.IO).launch {
                        plantDao.updatePlant(updatedPlant)
                        (context as Activity).runOnUiThread {
                            Toast.makeText(context, "Updated ✏️", Toast.LENGTH_SHORT).show()
                            refresh()
                        }
                    }
                }
                .setNegativeButton("Cancel", null)
                .show()
        }

        // 📄 OPEN DETAIL PAGE
        holder.itemView.setOnClickListener {
            val intent = Intent(context, PlantDetailActivity::class.java)
            intent.putExtra("plant_name", plant.name)
            intent.putExtra("plant_frequency", plant.frequency)
            intent.putExtra("plant_image", plant.imageUrl)
            context.startActivity(intent)
        }
    }
}
