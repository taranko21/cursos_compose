package com.example.cambio_formato_imagen

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import java.awt.Toolkit

fun main() = application {

    val url = object {}.javaClass.getResource("/image_edit.png")

    println(url)

    val awtIcon = url?.let {
        Toolkit.getDefaultToolkit().getImage(it)
    }

    Window(
        onCloseRequest = ::exitApplication,
        title = "Cambio formato imagen",
        resizable = false,
        state = WindowState(
            width = 500.dp,
            height = 900.dp
        )
    ) {
        awtIcon?.let {
            window.iconImage = it
        }

        HomeView()
    }
}