package com.example.movies_revews.navigation


import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.movies_revews.DataStore
import com.example.movies_revews.ui.theme.MyTheme
import com.example.movies_revews.views.LogScreen
import com.example.movies_revews.views.MoviesScreen

@Composable
fun AppNavigation(dataStore: DataStore) {
    var isDarkTheme by remember { mutableStateOf(false) }
    val navController = rememberNavController()
    LaunchedEffect(Unit) {
        dataStore.isDarkTheme.collect { isDark ->
            isDarkTheme = isDark
        }
    }
    MyTheme(darkTheme = isDarkTheme) {
        NavHost(navController = navController, startDestination = "login"){
            composable ("login"){
                LogScreen(navController,dataStore, isDarkTheme)
            }
            composable ("movies") {
                MoviesScreen(navController,isDarkTheme,dataStore)
            }
        }
    }
}