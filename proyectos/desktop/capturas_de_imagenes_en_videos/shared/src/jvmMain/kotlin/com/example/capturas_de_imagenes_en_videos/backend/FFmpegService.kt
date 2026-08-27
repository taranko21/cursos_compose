package com.example.capturas_de_imagenes_en_videos.backend

import java.io.File

class FFmpegService {

    fun extractFrame(
        videoPath: String,
        timestamp: String
    ): File {

        val outputFolder = getScreenshotsFolder()
        val fileName = generateRandomFileName()
        val outputFile = File(outputFolder, fileName)

        val command = listOf(
            "ffmpeg",
            "-ss", timestamp,
            "-i", videoPath,
            "-frames:v", "1",
            "-y",
            outputFile.absolutePath
        )

        val process = ProcessBuilder(command)
            .redirectErrorStream(true)
            .start()

        process.inputStream.bufferedReader().use { reader ->
            reader.forEachLine { line ->
                println(line)
            }
        }

        val exitCode = process.waitFor()

        if (exitCode != 0) {
            throw RuntimeException("FFmpeg no pudo generar la imagen")
        }

        return outputFile
    }
    fun getVideoDuration(videoPath: String): Double {

        val command = listOf(
            "ffprobe",
            "-v", "error",
            "-show_entries", "format=duration",
            "-of", "default=noprint_wrappers=1:nokey=1",
            videoPath
        )

        val process = ProcessBuilder(command)
            .redirectErrorStream(true)
            .start()

        val duration = process.inputStream
            .bufferedReader()
            .readText()
            .trim()

        val exitCode = process.waitFor()

        if (exitCode != 0) {
            throw RuntimeException(
                "No se pudo obtener la duración del video"
            )
        }

        return duration.toDouble()
    }

    fun extractFramesByInterval(
        videoPath: String,
        interval: Double
    ): List<File> {

        if (interval <= 0) {
            throw IllegalArgumentException(
                "El intervalo debe ser mayor que 0"
            )
        }

        val duration = getVideoDuration(videoPath)

        val generatedFiles = mutableListOf<File>()

        var timestamp = interval

        while (timestamp < duration) {

            val file = extractFrame(
                videoPath = videoPath,
                timestamp = formatTimestamp(timestamp)
            )

            generatedFiles.add(file)

            timestamp += interval
        }

        return generatedFiles
    }

    fun formatTimestamp(seconds: Double): String {

        val hours = (seconds / 3600).toInt()
        val minutes = ((seconds % 3600) / 60).toInt()
        val remainingSeconds = seconds % 60

        return String.format(
            "%02d:%02d:%06.3f",
            hours,
            minutes,
            remainingSeconds
        )
    }

    fun extractFramesAutomatically(
        videoPath: String
    ): List<File> {

        val duration = getVideoDuration(videoPath)

        if (duration <= 0) {
            throw IllegalArgumentException(
                "La duración del video no es válida"
            )
        }

        // Cantidad aproximada de imágenes que queremos generar
        val targetImages = 20

        // Calculamos automáticamente el intervalo
        var interval = duration / (targetImages + 1)

        // Intervalo mínimo de 100 ms
        if (interval < 0.1) {
            interval = 0.1
        }

        val generatedFiles = mutableListOf<File>()

        var timestamp = interval

        while (timestamp < duration) {

            val file = extractFrame(
                videoPath = videoPath,
                timestamp = formatTimestamp(timestamp)
            )

            generatedFiles.add(file)

            timestamp += interval
        }

        return generatedFiles
    }
}