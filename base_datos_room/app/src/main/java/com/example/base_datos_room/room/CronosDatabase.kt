package com.example.base_datos_room.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.base_datos_room.model.Users

@Database(entities = [Users::class], version = 1)
abstract  class AppDatabase : RoomDatabase(){
    abstract fun userDao(): UserDao
}