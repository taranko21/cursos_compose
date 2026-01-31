package com.example.datos_room.data.dataprovider

import android.content.Context
import androidx.room.Room
import com.example.datos_room.data.database.AppDatabase

object DatabaseProvider {
    fun getDatabase(context: Context): AppDatabase{
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }
}