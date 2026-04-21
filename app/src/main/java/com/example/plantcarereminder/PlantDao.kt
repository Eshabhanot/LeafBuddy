package com.example.plantcarereminder

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface PlantDao {

    // ➕ INSERT
    @Insert
    fun insertPlant(plant: Plant)

    // 📖 READ
    @Query("SELECT * FROM Plant")
    fun getAllPlants(): List<Plant>?

    // ✏️ UPDATE
    @Update
    fun updatePlant(plant: Plant)

    // 🗑️ DELETE
    @Delete
    fun deletePlant(plant: Plant)
}
