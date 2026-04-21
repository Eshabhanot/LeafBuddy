package com.example.plantcarereminder

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Plant(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val frequency: String,
    val imageUrl: String = ""
)