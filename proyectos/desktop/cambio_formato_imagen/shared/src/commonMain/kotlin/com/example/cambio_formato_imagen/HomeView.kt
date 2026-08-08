package com.example.cambio_formato_imagen


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.io.File

@Composable
@Preview
fun HomeView() {

    var carpeta by remember {
        mutableStateOf<File?>(null)
    }

    var archivosConvertidos by remember {
        mutableIntStateOf(0)
    }

    MaterialTheme {

        Scaffold(

            topBar = {

                CenterAlignedTopAppBar(

                    title = {
                        Text("Cambio de formato de imagen")
                    }

                )

            }

        ) { paddingValues ->

            Column(

                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(20.dp)

            ) {

                Text(
                    text = "Carpeta seleccionada",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(Modifier.height(8.dp))

                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Text(
                        text = carpeta?.absolutePath
                            ?: "No se ha seleccionado ninguna carpeta",
                        modifier = Modifier.padding(16.dp)
                    )

                }

                Spacer(Modifier.height(20.dp))

                Button(

                    modifier = Modifier.fillMaxWidth(),

                    onClick = {

                        carpeta = FilePicker.seleccionarCarpeta()

                    }

                ) {

                    Text("Buscar carpeta")

                }

                Spacer(Modifier.height(10.dp))

                Button(

                    modifier = Modifier.fillMaxWidth(),

                    enabled = carpeta != null,

                    onClick = {

                        carpeta?.let {

                            archivosConvertidos =
                                ImageRenamer.convertir(it)

                        }

                    }

                ) {

                    Text("Cambiar formato")

                }

                Spacer(Modifier.height(25.dp))

                Text(

                    text = "Archivos convertidos: $archivosConvertidos",

                    style = MaterialTheme.typography.titleMedium

                )

            }

        }

    }

}