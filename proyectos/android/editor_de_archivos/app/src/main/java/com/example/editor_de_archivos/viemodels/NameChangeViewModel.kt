package com.example.editor_de_archivos.viemodels

import android.app.Application
import android.net.Uri
import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.editor_de_archivos.data.FileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class NameChangeViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repository = FileRepository(application)

    private val _fileName = MutableStateFlow("")
    val fileName: StateFlow<String> =
        _fileName.asStateFlow()

    private val _selectedFiles =
        MutableStateFlow<List<DocumentFile>>(emptyList())

    private val _filesFound = MutableStateFlow(0)
    val filesFound: StateFlow<Int> =
        _filesFound.asStateFlow()

    private val _renameProgress = MutableStateFlow(0f)

    val renameProgress: StateFlow<Float> =
        _renameProgress.asStateFlow()

    private val _processedFiles = MutableStateFlow(0)

    val processedFiles: StateFlow<Int> =
        _processedFiles.asStateFlow()

    private val _renamedCount = MutableStateFlow(0)

    private val _isRenaming = MutableStateFlow(false)
    val isRenaming: StateFlow<Boolean> =
        _isRenaming.asStateFlow()

    private val _renameResult = MutableStateFlow<String?>(null)
    val renameResult: StateFlow<String?> =
        _renameResult.asStateFlow()

    // Guardamos qué tipo de selección hizo el usuario
    private var selectedFolderUri: Uri? = null
    private var selectedFileUri: Uri? = null

    private var renameJob: Job? = null

    fun selectFolder(
        uri: Uri,
        selectedType: String
    ) {
        selectedFolderUri = uri
        selectedFileUri = null

        updateFolderFiles(
            uri = uri,
            selectedType = selectedType
        )
    }

    fun selectFile(
        uri: Uri,
        selectedType: String
    ) {
        selectedFileUri = uri
        selectedFolderUri = null

        renameJob = viewModelScope.launch(Dispatchers.IO) {

            val isValid = repository.isValidFile(
                uri = uri,
                selectedType = selectedType
            )

            if (!isValid) {
                _fileName.value = ""
                _selectedFiles.value = emptyList()
                _filesFound.value = 0
                return@launch
            }

            val name = repository.getFileName(uri)

            val documentFile = DocumentFile.fromSingleUri(
                getApplication(),
                uri
            )

            if (documentFile != null) {
                _fileName.value = name ?: "Sin nombre"

                _selectedFiles.value =
                    listOf(documentFile)

                _filesFound.value = 1
            }
        }
    }

    fun updateSelectedType(
        selectedType: String
    ) {
        val folderUri = selectedFolderUri

        if (folderUri != null) {
            updateFolderFiles(
                uri = folderUri,
                selectedType = selectedType
            )
        }

        val fileUri = selectedFileUri

        if (fileUri != null) {
            selectFile(
                uri = fileUri,
                selectedType = selectedType
            )
        }
    }

    private fun updateFolderFiles(
        uri: Uri,
        selectedType: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {

            val name = repository.getDocumentName(uri)

            val files = repository.getFiles(
                uri = uri,
                selectedType = selectedType
            )

            _fileName.value =
                name ?: "Sin nombre"

            _selectedFiles.value = files

            _filesFound.value = files.size
        }
    }

    fun renameFiles() {

        val files = _selectedFiles.value

        if (files.isEmpty()) {
            _renameResult.value =
                "No hay archivos para renombrar"
            return
        }

        viewModelScope.launch(Dispatchers.IO) {

            _isRenaming.value = true
            _renameResult.value = null
            _renamedCount.value = 0

            try {

                val result = repository.renameFiles(
                    files = files,
                    onProgress = { current, total ->

                        _processedFiles.value = current

                        _renameProgress.value =
                            current.toFloat() / total.toFloat()
                    },
                    isCancelled = {
                        !isActive
                    }
                )

                _renamedCount.value =
                    result.renamed

                _renameResult.value =
                    if (result.renamed == result.total) {

                        "✓ ${result.renamed} de ${result.total} archivos renombrados correctamente"

                    } else {

                        "⚠ ${result.renamed} de ${result.total} archivos renombrados"
                    }

                if (result.renamed == result.total) {
                    clearSelection()
                }

            }catch (e: Exception) {
                e.printStackTrace()

                _renameResult.value =
                    "Error: ${e::class.simpleName}"
            }finally {

                _isRenaming.value = false
            }
        }
    }

    fun clearSelection() {
        selectedFolderUri = null
        selectedFileUri = null

        _selectedFiles.value = emptyList()
        _filesFound.value = 0
        _fileName.value = ""
    }

}