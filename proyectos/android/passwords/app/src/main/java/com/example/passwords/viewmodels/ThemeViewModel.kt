package com.example.passwords.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.passwords.DsDarkTheme
import kotlinx.coroutines.launch

class ThemeViewModel(application: Application): AndroidViewModel(application) {

    private val dsDarkTheme = DsDarkTheme(application)
    val isDarkTheme = dsDarkTheme.isDarkTheme
    fun toggleTheme(currrentValue: Boolean){
        viewModelScope.launch {
            dsDarkTheme.toogleTheme(currrentValue)
        }
    }
}