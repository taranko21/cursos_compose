package com.example.cambio_formato_imagen

import java.io.File
import javax.imageio.ImageIO


object ImageRenamer {

    fun convertir(carpeta: File): Int {

        var cantidad = 0

        carpeta.listFiles { file ->
            file.extension == "image"
        }?.forEach { archivo ->

            try {

                val formato = obtenerFormato(archivo)

                if (formato != null) {

                    val extension =
                        if (formato.lowercase() == "jpeg")
                            "jpg"
                        else
                            formato.lowercase()

                    val nuevoArchivo = File(
                        archivo.parentFile,
                        archivo.nameWithoutExtension + ".$extension"
                    )

                    if (archivo.renameTo(nuevoArchivo)) {
                        cantidad++
                    }

                }

            } catch (_: Exception) {
                // Ignorar errores
            }

        }

        return cantidad
    }

    private fun obtenerFormato(file: File): String? {

        ImageIO.createImageInputStream(file).use { stream ->

            val readers = ImageIO.getImageReaders(stream)

            if (readers.hasNext()) {
                return readers.next().formatName
            }

        }

        return null
    }
}