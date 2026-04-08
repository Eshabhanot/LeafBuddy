package com.example.plantcarereminder

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PlantDao {

    @Insert
    fun insertPlant(plant: Plant)

    @Query("SELECT * FROM Plant")
    fun getAllPlants(): List<Plant>?
}