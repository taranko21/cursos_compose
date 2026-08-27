package com.example.capturas_de_imagenes_en_videos.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.example.capturas_de_imagenes_en_videos.AutomaticView
import com.example.capturas_de_imagenes_en_videos.HomeView
import com.example.capturas_de_imagenes_en_videos.IntervalView
import com.example.capturas_de_imagenes_en_videos.TimeView

@Composable
fun NavigationViews(){
    val navController = remember {
        NavController()
    }

    when (navController.currentScreen) {

        Screen.Home -> {
            HomeView(navController)
        }

        Screen.Automatic -> {
            TimeView(navController)
        }

        Screen.Interval -> {
            IntervalView(navController)
        }

        Screen.Time -> {
            AutomaticView(navController)
        }
    }
}