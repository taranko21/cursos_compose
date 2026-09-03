package com.example.editor_de_archivos.views

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.editor_de_archivos.utils.ImageFormat
import com.example.editor_de_archivos.viemodels.FormatChangerViewModel

@Composable
fun FormatChanger(
    navController: NavController
) {

    val viewModel: FormatChangerViewModel = viewModel()

    val fileName by viewModel.fileName.collectAsState()

    val selectedFiles by
    viewModel.selectedFiles.collectAsState()

    val convertedFiles by
    viewModel.convertedFiles.collectAsState()

    val processedFiles by
    viewModel.processedFiles.collectAsState()

    val isConverting by
    viewModel.isConverting.collectAsState()

    var showSearchMenu by remember {
        mutableStateOf(false)
    }

    var selectedFormat by remember {
        mutableStateOf(ImageFormat.JPG)
    }

    val filePickerLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.OpenDocument()
        ) { uri ->

            if (uri != null) {
                viewModel.selectFile(uri)
            }
        }

    val folderPickerLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.OpenDocumentTree()
        ) { uri ->

            if (uri != null) {
                viewModel.selectFolder(uri)
            }
        }

    val createDocumentLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.CreateDocument()
        ) { uri ->

            if (uri != null) {
                viewModel.convertSelectedFile(
                    outputUri = uri,
                    format = selectedFormat
                )
            }
        }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFFF512F),
                        Color(0xFFDD2476)
                    )
                )
            )
    ) {

        Column(
            modifier = Modifier
                .padding(25.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(30.dp))

            // Título
            Text(
                text = "Elegir un archivo o carpeta:",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFF5F5F5)
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Archivo o carpeta seleccionada
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp)),
                shape = RoundedCornerShape(12.dp),
                color = Color.White.copy(alpha = 0.15f)
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        imageVector = Icons.Default.Folder,
                        contentDescription = "Carpeta",
                        tint = Color.White
                    )

                    Spacer(
                        modifier = Modifier.width(12.dp)
                    )

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {

                        Text(
                            text = "Archivo o carpeta seleccionada",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )

                        Spacer(
                            modifier = Modifier.height(4.dp)
                        )

                        Text(
                            text = fileName.ifEmpty {
                                "Ninguno seleccionado"
                            },
                            color = Color.White.copy(alpha = 0.8f),
                            fontSize = 14.sp,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }

            Spacer(
                modifier = Modifier.height(15.dp)
            )

            // Buscar archivo o carpeta
            Box {

                Button(
                    onClick = {
                        showSearchMenu = true
                    }
                ) {
                    Text("Buscar")
                }

                DropdownMenu(
                    expanded = showSearchMenu,
                    onDismissRequest = {
                        showSearchMenu = false
                    }
                ) {

                    DropdownMenuItem(
                        text = {
                            Text("Archivo")
                        },
                        onClick = {

                            showSearchMenu = false

                            filePickerLauncher.launch(
                                arrayOf("image/*")
                            )
                        }
                    )

                    DropdownMenuItem(
                        text = {
                            Text("Carpeta")
                        },
                        onClick = {

                            showSearchMenu = false

                            folderPickerLauncher.launch(null)
                        }
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(45.dp)
            )

            // Formato
            Text(
                text = "Formato de conversión",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFF5F5F5)
            )

            Spacer(
                modifier = Modifier.height(15.dp)
            )

            // Selección de formato
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                ImageFormat.entries.forEach { format ->

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        RadioButton(
                            selected = selectedFormat == format,
                            onClick = {
                                selectedFormat = format
                            }
                        )

                        Text(
                            text = format.extension.uppercase(),
                            color = Color.White
                        )
                    }
                }
            }

            Spacer(
                modifier = Modifier.height(25.dp)
            )

            Text(
                text = "Archivos encontrados: ${selectedFiles.size}",
                color = Color.White
            )

            Spacer(
                modifier = Modifier.height(25.dp)
            )

            // Cambiar formato
            Button(
                onClick = {

                    if (viewModel.isFolderSelected()) {

                        // Conversión de carpeta
                        viewModel.convertFiles(selectedFormat)

                    } else {

                        // Conversión de archivo
                        val fileName =
                            selectedFiles.firstOrNull()?.name
                                ?: "imagen"

                        val baseName =
                            fileName.substringBeforeLast(
                                '.',
                                fileName
                            )

                        val newName =
                            "$baseName.${selectedFormat.extension}"

                        createDocumentLauncher.launch(newName)
                    }
                },
                enabled = selectedFiles.isNotEmpty() && !isConverting
            ) {
                Text("Cambiar formato")
            }

            if (isConverting) {

                Spacer(
                    modifier = Modifier.height(20.dp)
                )

                Text(
                    text = "Convirtiendo archivos...",
                    color = Color.White,
                    fontSize = 16.sp
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                LinearProgressIndicator(
                    progress = {
                        if (selectedFiles.isNotEmpty()) {
                            processedFiles.toFloat() /
                                    selectedFiles.size.toFloat()
                        } else {
                            0f
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(
                            RoundedCornerShape(8.dp)
                        )
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text = "$processedFiles / ${selectedFiles.size}",
                    color = Color.White,
                    fontSize = 14.sp
                )
            }

            Spacer(
                modifier = Modifier.height(35.dp)
            )

            // Archivos convertidos
            Text(
                text = "Archivos convertidos: $convertedFiles",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFF5F5F5)
            )

            Spacer(
                modifier = Modifier.height(50.dp)
            )

            // Regresar
            Button(
                onClick = {
                    navController.navigate("HomeView")
                }
            ) {
                Text("Atrás")
            }
        }
    }
}