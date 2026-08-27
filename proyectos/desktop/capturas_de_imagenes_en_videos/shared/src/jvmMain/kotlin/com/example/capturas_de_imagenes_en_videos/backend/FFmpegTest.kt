package com.example.capturas_de_imagenes_en_videos.backend

fun main() {

    val ffmpeg = FFmpegService()

    val images = ffmpeg.extractFramesAutomatically(
        videoPath = "/home/krilin/Downloads/Outfits.mp4",
    )

    println("Imágenes generadas: ${images.size}")

    images.forEach {
        println(it.absolutePath)
    }
}