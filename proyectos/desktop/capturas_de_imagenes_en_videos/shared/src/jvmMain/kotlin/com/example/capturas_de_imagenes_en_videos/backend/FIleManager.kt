package com.example.capturas_de_imagenes_en_videos.backend

import java.io.File
import java.util.UUID

fun getScreenshotsFolder(): File {

    val picturesFolder = File(
        System.getProperty("user.home"),
        "Pictures"
    )

    val screenshotsFolder = File(
        picturesFolder,
        "screenshots"
    )

    if (!screenshotsFolder.exists()) {
        screenshotsFolder.mkdirs()
    }

    return screenshotsFolder
}

fun generateRandomFileName(): String {
    return "${UUID.randomUUID()}.jpg"
}