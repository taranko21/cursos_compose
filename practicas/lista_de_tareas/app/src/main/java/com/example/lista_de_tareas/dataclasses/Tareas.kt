package com.example.lista_de_tareas.dataclasses

data class Tareas(
    var id: Int,
    var tareas: String,
    val completada: Boolean = false
)
