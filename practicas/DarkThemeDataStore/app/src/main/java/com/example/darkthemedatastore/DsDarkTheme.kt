package com.example.darkthemedatastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "settings")
class DsDarkTheme (private val context: Context){
        private val dataStore= context.dataStore
        private val DARK_THEME_KEY = booleanPreferencesKey("dark_theme")

    val isDarkTheme: Flow<Boolean> = dataStore.data.map { preference ->
        preference[DARK_THEME_KEY] ?: false
    }

    suspend fun toogleTheme(currentState: Boolean){
        dataStore.edit { preference ->
            preference[DARK_THEME_KEY] = !currentState
        }
    }

}