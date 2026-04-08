package com.example.plantcarereminder

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {

    @Insert
    fun registerUser(user: User)

    @Query("SELECT * FROM User WHERE email = :email AND password = :password")
    fun login(email: String, password: String): User?
}