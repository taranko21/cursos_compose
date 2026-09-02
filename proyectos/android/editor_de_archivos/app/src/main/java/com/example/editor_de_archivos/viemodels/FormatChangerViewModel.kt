package com.example.editor_de_archivos.viemodels

import android.app.Application
import android.net.Uri
import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.editor_de_archivos.data.FormatRepository
import com.example.editor_de_archivos.utils.ImageFormat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FormatChangerViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repository =
        FormatRepository(application)

    private val _fileName =
        MutableStateFlow("")

    val fileName: StateFlow<String> =
        _fileName.asStateFlow()

    private val _selectedFiles =
        MutableStateFlow<List<DocumentFile>>(emptyList())

    val selectedFiles: StateFlow<List<DocumentFile>> =
        _selectedFiles.asStateFlow()

    private val _convertedFiles =
        MutableStateFlow(0)

    val convertedFiles: StateFlow<Int> =
        _convertedFiles.asStateFlow()

    private val _processedFiles =
        MutableStateFlow(0)

    val processedFiles: StateFlow<Int> =
        _processedFiles.asStateFlow()

    private val _isConverting =
        MutableStateFlow(false)

    val isConverting: StateFlow<Boolean> =
        _isConverting.asStateFlow()

    private var selectedFolderUri: Uri? = null
    private var selectedFileUri: Uri? = null

    fun selectFolder(uri: Uri) {

        selectedFolderUri = uri
        selectedFileUri = null

        viewModelScope.launch(Dispatchers.IO) {

            val name =
                repository.getDocumentName(uri)

            val files =
                repository.getImageFiles(uri)

            _fileName.value =
                name ?: "Sin nombre"

            _selectedFiles.value =
                files
        }
    }

    fun selectFile(uri: Uri) {

        selectedFileUri = uri
        selectedFolderUri = null

        viewModelScope.launch(Dispatchers.IO) {

            val isImage =
                repository.isImage(uri)

            if (!isImage) {

                _fileName.value = ""
                _selectedFiles.value =
                    emptyList()

                return@launch
            }

            val name =
                repository.getFileName(uri)

            val documentFile =
                DocumentFile.fromSingleUri(
                    getApplication(),
                    uri
                )

            if (documentFile != null) {

                _fileName.value =
                    name ?: "Sin nombre"

                _selectedFiles.value =
                    listOf(documentFile)
            }
        }
    }
    fun convertFiles(
        format: ImageFormat
    ) {

        val files =
            _selectedFiles.value

        if (files.isEmpty()) {
            return
        }

        viewModelScope.launch(Dispatchers.IO) {

            _isConverting.value = true
            _processedFiles.value = 0
            _convertedFiles.value = 0

            var converted = 0

            val totalFiles =
                files.size

            for (file in files) {

                val success =
                    repository.convertImage(
                        file = file,
                        format = format
                    )

                if (success) {
                    converted++
                }

                _processedFiles.value++
            }

            _convertedFiles.value =
                converted

            _isConverting.value = false
        }
    }


}