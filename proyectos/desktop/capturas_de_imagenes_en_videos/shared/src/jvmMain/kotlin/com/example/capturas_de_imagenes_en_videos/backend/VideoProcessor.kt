package com.example.capturas_de_imagenes_en_videos.backend

class VideoProcessor {

    fun extractFrame(
        videoPath: String,
        timestamp: Double,
        outputPath: String
    ) {
        val process = ProcessBuilder(
            "ffmpeg",
            "-ss", timestamp.toString(),
            "-i", videoPath,
            "-frames:v", "1",
            "-y",
            outputPath
        )
            .redirectErrorStream(true)
            .start()

        process.waitFor()
    }
}