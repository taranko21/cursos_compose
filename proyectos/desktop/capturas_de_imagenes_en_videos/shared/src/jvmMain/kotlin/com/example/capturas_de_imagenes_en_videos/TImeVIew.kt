package com.example.capturas_de_imagenes_en_videos

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.capturas_de_imagenes_en_videos.backend.FFmpegService
import javax.swing.JFileChooser
import javax.swing.filechooser.FileNameExtensionFilter
import com.example.capturas_de_imagenes_en_videos.navigation.NavController
import com.example.capturas_de_imagenes_en_videos.navigation.Screen

@Composable
fun TimeView(navController: NavController) {

    var videoPath by remember { mutableStateOf("") }
    var timestamp by remember { mutableStateOf("") }

    val ffmpegService = remember {
        FFmpegService()
    }

    MaterialTheme {

        Scaffold { paddingValues ->

            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
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

                Spacer(modifier = Modifier.height(30.dp))

                Button(
                    onClick = {

                        val chooser = JFileChooser()

                        chooser.fileFilter = FileNameExtensionFilter(
                            "Videos",
                            "mp4",
                            "mkv",
                            "avi",
                            "mov"
                        )

                        val result = chooser.showOpenDialog(null)

                        if (result == JFileChooser.APPROVE_OPTION) {
                            videoPath = chooser.selectedFile.absolutePath
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

                TextField(
                    value = timestamp,
                    onValueChange = {
                        timestamp = it
                    },
                    label = {
                        Text("Timestamp")
                    },
                    placeholder = {
                        Text("00:00:04.500")
                    }
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    "Generar imagen:",
                    fontSize = 20.sp,
                    fontWeight = FontWeight(100)
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    enabled = videoPath.isNotEmpty() &&
                            timestamp.isNotEmpty(),

                    onClick = {

                        try {

                            val image = ffmpegService.extractFrame(
                                videoPath = videoPath,
                                timestamp = timestamp
                            )

                            println("Imagen generada:")
                            println(image.absolutePath)

                        } catch (e: Exception) {

                            println("Error: ${e.message}")

                        }
                    },

                    modifier = Modifier
                        .width(200.dp)
                        .height(50.dp),

                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF2D1B2E)
                    )
                ) {
                    Text("Generar")
                }

                Spacer(modifier = Modifier.height(20.dp))

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