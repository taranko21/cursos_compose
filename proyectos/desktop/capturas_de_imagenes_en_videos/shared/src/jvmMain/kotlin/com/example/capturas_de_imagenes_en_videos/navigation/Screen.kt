package com.example.capturas_de_imagenes_en_videos.navigation

sealed class Screen {
    data object Home : Screen()
    data object Automatic : Screen()
    data object Interval : Screen()
    data object Time : Screen()
}