package com.example.base_datos_room.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.base_datos_room.model.Users

@Dao
interface UserDao{
    @Query("SELECT * FROM users")
    fun getAllUsers(): List<Users>

    @Insert
    fun insertAll(vararg users: Users)
}