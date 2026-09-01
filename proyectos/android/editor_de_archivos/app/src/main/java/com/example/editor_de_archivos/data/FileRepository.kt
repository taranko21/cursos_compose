package com.example.editor_de_archivos.data

import android.content.Context
import android.net.Uri
import androidx.documentfile.provider.DocumentFile
import com.example.editor_de_archivos.utils.RandomNameGenerator


data class RenameResult(
    val total: Int,
    val renamed: Int
)


class FileRepository(
    private val context: Context
) {

    fun getDocumentName(uri: Uri): String? {

        val documentFile = DocumentFile.fromTreeUri(
            context,
            uri
        )

        return documentFile?.name
    }

    fun getFiles(
        uri: Uri,
        selectedType: String
    ): List<DocumentFile> {

        val directory = DocumentFile.fromTreeUri(
            context,
            uri
        ) ?: return emptyList()

        return directory.listFiles().filter { file ->

            if (!file.isFile) {
                return@filter false
            }

            val mimeType = file.type

            val isImage = mimeType?.startsWith("image/") == true
            val isVideo = mimeType?.startsWith("video/") == true

            when (selectedType) {

                "Imagenes" -> isImage

                "Videos" -> isVideo

                "Ambos" -> isImage || isVideo

                else -> false
            }
        }
    }
    fun renameFiles(
        files: List<DocumentFile>,
        onProgress: (current: Int, total: Int) -> Unit,
        isCancelled: () -> Boolean
    ): RenameResult {

        var renamedCount = 0
        var processedCount = 0

        val existingNames = files
            .mapNotNull { it.name }
            .toMutableSet()

        for (file in files) {

            if (isCancelled()) {
                break
            }

            val originalName = file.name

            if (originalName != null) {

                val extension = originalName.substringAfterLast(
                    '.',
                    missingDelimiterValue = ""
                )

                var newName: String

                do {
                    val randomName =
                        RandomNameGenerator.generate()

                    newName = if (extension.isNotEmpty()) {
                        "$randomName.$extension"
                    } else {
                        randomName
                    }

                } while (newName in existingNames)

                if (file.renameTo(newName)) {

                    existingNames.remove(originalName)
                    existingNames.add(newName)

                    renamedCount++
                }
            }

            processedCount++

            onProgress(
                processedCount,
                files.size
            )
        }

        return RenameResult(
            total = files.size,
            renamed = renamedCount
        )
    }

    fun getFileName(uri: Uri): String? {

        val documentFile = DocumentFile.fromSingleUri(
            context,
            uri
        )

        return documentFile?.name
    }

    fun isValidFile(
        uri: Uri,
        selectedType: String
    ): Boolean {

        val mimeType = context.contentResolver.getType(uri)

        val isImage = mimeType?.startsWith("image/") == true
        val isVideo = mimeType?.startsWith("video/") == true

        return when (selectedType) {

            "Imagenes" -> isImage

            "Videos" -> isVideo

            "Ambos" -> isImage || isVideo

            else -> false
        }
    }

}