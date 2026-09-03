package com.example.editor_de_archivos.data

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.provider.DocumentsContract
import android.util.Log
import androidx.documentfile.provider.DocumentFile
import android.net.Uri
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
        directoryUri: Uri,
        format: ImageFormat
    ): Boolean {

        Log.d("FORMAT_DEBUG", "1. Inicio")

        val inputStream =
            context.contentResolver.openInputStream(
                file.uri
            ) ?: return false

        Log.d("FORMAT_DEBUG", "2. InputStream abierto")

        val bitmap =
            inputStream.use {
                BitmapFactory.decodeStream(it)
            } ?: return false

        Log.d("FORMAT_DEBUG", "3. Bitmap creado")

        val originalName =
            file.name ?: return false

        Log.d(
            "FORMAT_DEBUG",
            "4. Nombre original: $originalName"
        )

        Log.d(
            "FORMAT_DEBUG",
            "URI DEL ARCHIVO: ${file.uri}"
        )

        val baseName =
            originalName.substringBeforeLast(
                '.',
                originalName
            )

        val newName =
            "$baseName.${format.extension}"

        Log.d(
            "FORMAT_DEBUG",
            "5. Nuevo nombre: $newName"
        )

        val mimeType = when (format) {

            ImageFormat.JPG ->
                "image/jpeg"

            ImageFormat.PNG ->
                "image/png"

            ImageFormat.WEBP ->
                "image/webp"
        }

        Log.d(
            "FORMAT_DEBUG",
            "6. URI de directorio: $directoryUri"
        )

        Log.d(
            "FORMAT_DEBUG",
            "7. MIME: $mimeType"
        )

        val documentId =
            DocumentsContract.getTreeDocumentId(directoryUri)

        val directoryDocumentUri =
            DocumentsContract.buildDocumentUriUsingTree(
                directoryUri,
                documentId
            )

        Log.d(
            "FORMAT_DEBUG",
            "URI documento del directorio: $directoryDocumentUri"
        )

        val newFileUri =
            DocumentsContract.createDocument(
                context.contentResolver,
                directoryDocumentUri,
                mimeType,
                newName
            ) ?: return false

        Log.d(
            "FORMAT_DEBUG",
            "8. Nuevo archivo creado: $newFileUri"
        )

        val outputStream =
            context.contentResolver.openOutputStream(
                newFileUri
            ) ?: return false

        Log.d(
            "FORMAT_DEBUG",
            "9. OutputStream abierto"
        )

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

        Log.d(
            "FORMAT_DEBUG",
            "10. Intentando comprimir"
        )

        val success =
            outputStream.use {

                bitmap.compress(
                    compressFormat,
                    90,
                    it
                )
            }

        Log.d(
            "FORMAT_DEBUG",
            "11. Compress resultado: $success"
        )

        bitmap.recycle()

        Log.d(
            "FORMAT_DEBUG",
            "12. Conversión terminada"
        )

        return success
    }
    fun convertSingleImage(
        fileUri: Uri,
        outputUri: Uri,
        format: ImageFormat
    ): Boolean {

        Log.d("FORMAT_DEBUG", "=== CONVERSIÓN DE ARCHIVO ===")
        Log.d("FORMAT_DEBUG", "Input URI: $fileUri")
        Log.d("FORMAT_DEBUG", "Output URI: $outputUri")

        val inputStream =
            context.contentResolver.openInputStream(fileUri)
                ?: return false

        val bitmap =
            inputStream.use {
                BitmapFactory.decodeStream(it)
            } ?: return false

        Log.d(
            "FORMAT_DEBUG",
            "Bitmap creado"
        )

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

        val outputStream =
            context.contentResolver.openOutputStream(outputUri)
                ?: return false

        Log.d(
            "FORMAT_DEBUG",
            "OutputStream abierto"
        )

        val success =
            outputStream.use {
                bitmap.compress(
                    compressFormat,
                    90,
                    it
                )
            }

        Log.d(
            "FORMAT_DEBUG",
            "Resultado conversión: $success"
        )

        bitmap.recycle()

        return success
    }
}