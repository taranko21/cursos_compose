package com.example.cambio_formato_imagen

import java.io.File
import javax.swing.JFileChooser

object FilePicker {

    fun seleccionarCarpeta(): File? {

        val chooser = JFileChooser().apply {
            fileSelectionMode = JFileChooser.DIRECTORIES_ONLY
        }

        return if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            chooser.selectedFile
        } else {
            null
        }
    }
}