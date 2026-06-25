package com.example.images_videos_name_change

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import java.io.File
import javax.swing.JFileChooser
import kotlin.random.Random

class HomeViewModel {

    var folderPath by mutableStateOf("")
        private set

    var renamedFilesCount by mutableStateOf(0)
        private set

    var statusMessage by mutableStateOf("")
        private set

    fun selectFolder() {
        val chooser = JFileChooser().apply {
            fileSelectionMode = JFileChooser.DIRECTORIES_ONLY
            isAcceptAllFileFilterUsed = false
            dialogTitle = "Select the folder containing the files"
        }

        val result = chooser.showOpenDialog(null)

        if (result == JFileChooser.APPROVE_OPTION) {
            folderPath = chooser.selectedFile.absolutePath

            renamedFilesCount = 0
            statusMessage = ""

            println("Selected folder: $folderPath")
        }
    }

    fun renameFiles(fileType: FileType) {
        renamedFilesCount = 0
        statusMessage = ""

        if (folderPath.isBlank()) {
            statusMessage = "Select a folder first"
            return
        }

        val folder = File(folderPath)

        if (!folder.exists()) {
            statusMessage = "The selected folder does not exist"
            return
        }

        if (!folder.isDirectory) {
            statusMessage = "The selected path is not a folder"
            return
        }

        val extensions = when (fileType) {
            FileType.IMAGES -> setOf(
                "jpg", "jpeg", "png",
                "gif", "bmp", "webp"
            )

            FileType.VIDEOS -> setOf(
                "mp4", "mkv", "avi",
                "mov", "wmv", "webm"
            )

            FileType.BOTH -> setOf(
                "jpg", "jpeg", "png",
                "gif", "bmp", "webp",
                "mp4", "mkv", "avi",
                "mov", "wmv", "webm"
            )
        }

        val filesToRename = folder.listFiles()
            ?.filter { file ->
                println("File found: ${file.name}")

                file.isFile &&
                        file.extension.lowercase() in extensions
            }
            ?: emptyList()

        if (filesToRename.isEmpty()) {
            statusMessage = "No compatible files were found in this folder"
            return
        }

        var count = 0

        filesToRename.forEach { file ->
            val extension = file.extension.lowercase()

            var newFile: File

            do {
                val randomName = generateRandomName()
                newFile = File(folder, "$randomName.$extension")
            } while (newFile.exists())

            val renamed = file.renameTo(newFile)

            println(
                "${file.name} -> ${newFile.name} | Success: $renamed"
            )

            if (renamed) {
                count++
            }
        }

        renamedFilesCount = count

        statusMessage = if (count > 0) {
            "Successfully renamed $count file(s)"
        } else {
            "Could not rename the files"
        }
    }

    private fun generateRandomName(length: Int = 8): String {
        val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"

        return buildString {
            repeat(length) {
                append(chars[Random.nextInt(chars.length)])
            }
        }
    }
}