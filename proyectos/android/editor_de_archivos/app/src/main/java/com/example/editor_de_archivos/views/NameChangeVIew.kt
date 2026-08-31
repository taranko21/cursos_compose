package com.example.editor_de_archivos.views

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
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.navigation.NavController

@Composable
fun NameChangeView(
    navController: NavController
) {

    var fileName by remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf("Imagenes") }

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

            // Tipo de archivo
            Text(
                text = "Elegir qué tipo de archivo quieres cambiar:",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFF5F5F5)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                RadioButton(
                    selected = selectedType == "Imagenes",
                    onClick = {
                        selectedType = "Imagenes"
                    }
                )

                Text(
                    text = "Imágenes",
                    color = Color(0xFFF5F5F5)
                )

                Spacer(modifier = Modifier.width(10.dp))

                RadioButton(
                    selected = selectedType == "Videos",
                    onClick = {
                        selectedType = "Videos"
                    }
                )

                Text(
                    text = "Videos",
                    color = Color(0xFFF5F5F5)
                )

                Spacer(modifier = Modifier.width(10.dp))

                RadioButton(
                    selected = selectedType == "Ambos",
                    onClick = {
                        selectedType = "Ambos"
                    }
                )

                Text(
                    text = "Ambos",
                    color = Color(0xFFF5F5F5)
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            // Selección de archivo
            Text(
                text = "Elegir un archivo o carpeta:",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFF5F5F5)
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Archivo seleccionado
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

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {

                        Text(
                            text = "Archivo o carpeta seleccionada",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )

                        Spacer(modifier = Modifier.height(4.dp))

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

            Spacer(modifier = Modifier.height(15.dp))

            // Botón buscar
            Button(
                onClick = {
                    // Aquí posteriormente abriremos
                    // el selector de archivos/carpeta.
                }
            ) {
                Text("Buscar")
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Número de archivos
            Text(
                text = "Archivos encontrados: 0",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFF5F5F5)
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Botón renombrar
            Button(
                onClick = {
                    // Aquí posteriormente irá
                    // la lógica para renombrar.
                }
            ) {
                Text("Renombrar")
            }

            Spacer(modifier = Modifier.height(40.dp))

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