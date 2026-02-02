package com.example.room_and_hilt.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.room_and_hilt.data.entity.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert
    suspend fun insert(user: User)

    @Query("SELECT * FROM users")
    fun getUsers(): Flow<List<User>>
}
