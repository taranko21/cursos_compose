package com.example.generador_de_contrasenas

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Generador de Contraseñas",
        resizable = false,
        state = WindowState(
            width = 800.dp,
            height = 600.dp
        )
    ) {
        App()
    }
}