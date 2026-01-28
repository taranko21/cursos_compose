package com.example.base_datos_room.inidatabase

import android.app.Application
import androidx.room.Room
import com.example.base_datos_room.room.AppDatabase

class IniDataBase : Application() {
    val database by lazy{
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }
}