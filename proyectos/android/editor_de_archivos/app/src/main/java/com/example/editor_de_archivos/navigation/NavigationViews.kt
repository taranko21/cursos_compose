package com.example.editor_de_archivos.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.editor_de_archivos.views.FormatChanger
import com.example.editor_de_archivos.views.HomeView
import com.example.editor_de_archivos.views.NameChangeView

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController,
        "HomeView"
    ) {
        composable("HomeView") {
            HomeView(navController)
        }

        composable("NameChangeView") {
            NameChangeView(navController)
        }

        composable("FormatChange") { FormatChanger(navController) }
    }
}