package com.example.images_videos_name_change

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Files renamer",
        resizable = false,
        state = WindowState(
            width = 500.dp,
            height = 900.dp,
        )
    ) {
        HomeView()
    }
}