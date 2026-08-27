package com.example.capturas_de_imagenes_en_videos.navigation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class NavController {
    var currentScreen by mutableStateOf<Screen>(Screen.Home)
        private set

    fun navigate(screen: Screen) {
        currentScreen = screen
    }
}