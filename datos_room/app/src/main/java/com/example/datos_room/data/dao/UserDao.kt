package com.example.datos_room.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.datos_room.data.entity.UsersEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert
    suspend fun insert(user: UsersEntity)

    @Query("SELECT * FROM users")
    fun getUsers(): Flow<List<UsersEntity>>

}