package com.example.datos_room.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.datos_room.data.dao.UserDao
import com.example.datos_room.data.entity.UsersEntity


@Database(entities = [UsersEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
}