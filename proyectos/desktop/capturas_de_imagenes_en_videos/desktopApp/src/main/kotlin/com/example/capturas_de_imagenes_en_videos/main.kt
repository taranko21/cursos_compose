package com.example.capturas_de_imagenes_en_videos

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import com.example.capturas_de_imagenes_en_videos.navigation.NavigationViews
import java.awt.Toolkit

fun main() = application {
    val url = object {}.javaClass.getResource("/video_2image.png")

    println(url)

    val awtIcon = url?.let {
        Toolkit.getDefaultToolkit().getImage(it)
    }
    Window(
        onCloseRequest = ::exitApplication,
        title = "Capturas de imagenes en videos",
        resizable = false,
        state = WindowState(
            width = 500.dp,
            height = 900.dp
        )
    ) {
        awtIcon?.let {
            window.iconImage = it
        }
        NavigationViews()
    }
}