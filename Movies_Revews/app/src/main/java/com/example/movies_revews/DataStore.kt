package com.example.movies_revews

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


private val Context.dataStore by preferencesDataStore(name = "theme_selector")

class DataStore(context: Context) {
    private val dataStore = context.dataStore


    companion object{
        private val DARK_THEME_KEY = booleanPreferencesKey("dark_theme")
    }
    val isDarkTheme: Flow<Boolean> = dataStore.data.map {
        preferences ->
        preferences[DARK_THEME_KEY] ?: false
    }
    suspend fun saveTheme(isDark: Boolean){
        dataStore.edit { preferences ->
            preferences[DARK_THEME_KEY] = isDark
        }
    }
}