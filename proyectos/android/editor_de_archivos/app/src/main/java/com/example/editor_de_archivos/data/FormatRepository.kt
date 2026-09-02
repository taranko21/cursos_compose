package com.example.editor_de_archivos.data

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.documentfile.provider.DocumentFile
import com.example.editor_de_archivos.utils.ImageFormat

class FormatRepository(
    private val context: Context
) {

    fun getDocumentName(uri: Uri): String? {

        val documentFile =
            DocumentFile.fromTreeUri(
                context,
                uri
            )

        return documentFile?.name
    }

    fun getFileName(uri: Uri): String? {

        val documentFile =
            DocumentFile.fromSingleUri(
                context,
                uri
            )

        return documentFile?.name
    }

    fun getImageFiles(
        uri: Uri
    ): List<DocumentFile> {

        val directory =
            DocumentFile.fromTreeUri(
                context,
                uri
            ) ?: return emptyList()

        val imageExtensions = setOf(
            "jpg",
            "jpeg",
            "png",
            "webp",
            "image"
        )

        return directory.listFiles().filter { file ->

            if (!file.isFile) {
                return@filter false
            }

            val name =
                file.name ?: return@filter false

            val extension =
                name.substringAfterLast(
                    '.',
                    ""
                ).lowercase()

            extension in imageExtensions
        }
    }

    fun isImage(
        uri: Uri
    ): Boolean {

        val mimeType =
            context.contentResolver.getType(uri)

        return mimeType?.startsWith("image/") == true
    }

    fun convertImage(
        file: DocumentFile,
        format: ImageFormat
    ): Boolean {

        val inputStream =
            context.contentResolver.openInputStream(
                file.uri
            ) ?: return false

        val bitmap =
            inputStream.use {
                BitmapFactory.decodeStream(it)
            } ?: return false

        val originalName =
            file.name ?: return false

        val baseName =
            originalName.substringBeforeLast(
                '.',
                originalName
            )

        val newName =
            "$baseName.${format.extension}"

        val parent =
            file.parentFile ?: return false

        val mimeType = when (format) {
            ImageFormat.JPG -> "image/jpeg"
            ImageFormat.PNG -> "image/png"
            ImageFormat.WEBP -> "image/webp"
        }

        val newFile =
            parent.createFile(
                mimeType,
                newName
            ) ?: return false

        val outputStream =
            context.contentResolver.openOutputStream(
                newFile.uri
            ) ?: return false

        val compressFormat =
            when (format) {

                ImageFormat.JPG ->
                    Bitmap.CompressFormat.JPEG

                ImageFormat.PNG ->
                    Bitmap.CompressFormat.PNG

                ImageFormat.WEBP ->
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        Bitmap.CompressFormat.WEBP_LOSSY
                    } else {
                        @Suppress("DEPRECATION")
                        Bitmap.CompressFormat.WEBP
                    }
            }

        val success =
            outputStream.use {
                bitmap.compress(
                    compressFormat,
                    90,
                    it
                )
            }

        bitmap.recycle()

        return success
    }
}