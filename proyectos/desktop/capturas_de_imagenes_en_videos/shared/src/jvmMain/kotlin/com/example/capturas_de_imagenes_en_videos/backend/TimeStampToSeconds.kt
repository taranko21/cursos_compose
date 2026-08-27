package com.example.capturas_de_imagenes_en_videos.backend

fun timestampToSeconds(timestamp: String): Double {
    val parts = timestamp.split(":")

    if (parts.size != 3) {
        throw IllegalArgumentException("Formato inválido")
    }

    val hours = parts[0].toDouble()
    val minutes = parts[1].toDouble()
    val seconds = parts[2].toDouble()

    return hours * 3600 + minutes * 60 + seconds
}