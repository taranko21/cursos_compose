package com.example.capturas_de_imagenes_en_videos

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.capturas_de_imagenes_en_videos.backend.FFmpegService
import com.example.capturas_de_imagenes_en_videos.backend.timestampToSeconds
import com.example.capturas_de_imagenes_en_videos.navigation.NavController
import com.example.capturas_de_imagenes_en_videos.navigation.Screen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.swing.JFileChooser

@Composable
fun IntervalView(navController: NavController) {

    var videoPath by remember { mutableStateOf("") }
    var interval by remember { mutableStateOf("") }
    var generatedImages by remember { mutableStateOf(0) }
    var isGenerating by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()

    val ffmpegService = remember {
        FFmpegService()
    }

    MaterialTheme {

        Scaffold { paddingValues ->

            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFFFF512F),
                                Color(0xFFDD2476)
                            )
                        )
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    "Elegir video:",
                    fontSize = 30.sp,
                    fontWeight = FontWeight(100)
                )

                Spacer(modifier = Modifier.height(30.dp))

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF2D1B2E)
                    )
                ) {

                    Text(
                        text = if (videoPath.isEmpty()) {
                            "Ningún video seleccionado"
                        } else {
                            videoPath.substringAfterLast("/")
                        },
                        modifier = Modifier.padding(16.dp),
                        fontSize = 15.sp,
                        fontWeight = FontWeight(100),
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = {

                        val fileChooser = JFileChooser()

                        val result = fileChooser.showOpenDialog(null)

                        if (result == JFileChooser.APPROVE_OPTION) {
                            videoPath =
                                fileChooser.selectedFile.absolutePath
                        }
                    },
                    modifier = Modifier
                        .width(200.dp)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF2D1B2E)
                    )
                ) {
                    Text("Abrir")
                }

                Spacer(modifier = Modifier.height(30.dp))

                Text(
                    "Intervalo entre capturas:",
                    fontSize = 20.sp,
                    fontWeight = FontWeight(100)
                )

                Spacer(modifier = Modifier.height(30.dp))

                TextField(
                    value = interval,
                    onValueChange = {
                        interval = it
                    },
                    label = {
                        Text("Intervalo")
                    },
                    placeholder = {
                        Text("00:00:04.500")
                    }
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    enabled = videoPath.isNotEmpty() &&
                            interval.isNotEmpty() &&
                            !isGenerating,

                    onClick = {

                        scope.launch {

                            try {

                                isGenerating = true

                                val intervalSeconds =
                                    timestampToSeconds(interval)

                                val images = kotlinx.coroutines.withContext(
                                    Dispatchers.IO
                                ) {

                                    ffmpegService.extractFramesByInterval(
                                        videoPath = videoPath,
                                        interval = intervalSeconds
                                    )
                                }

                                generatedImages = images.size

                            } catch (e: Exception) {

                                e.printStackTrace()

                            } finally {

                                isGenerating = false
                            }
                        }
                    },

                    modifier = Modifier
                        .width(200.dp)
                        .height(50.dp),

                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF2D1B2E)
                    )
                ) {

                    Text(
                        if (isGenerating) {
                            "Generando..."
                        } else {
                            "Generar"
                        }
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    "Imágenes generadas: $generatedImages"
                )

                Spacer(modifier = Modifier.height(30.dp))

                Button(
                    onClick = {
                        navController.navigate(Screen.Home)
                    },
                    modifier = Modifier
                        .width(200.dp)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF2D1B2E)
                    )
                ) {
                    Text("Regresar")
                }

                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}