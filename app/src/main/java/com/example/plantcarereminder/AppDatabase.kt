package com.example.plantcarereminder

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class, Plant::class], version = 3)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun plantDao(): PlantDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "plant_db"
                )
                    .fallbackToDestructiveMigration() // 🔥 important
                    .allowMainThreadQueries() // for now ok
                    .build()
            }
            return INSTANCE!!
        }
    }
}