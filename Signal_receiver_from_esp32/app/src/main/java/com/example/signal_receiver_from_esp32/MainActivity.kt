package com.example.signal_receiver_from_esp32

// build.gradle (nivel de módulo): asegúrate de usar esto en dependencies
// implementation "androidx.compose.ui:ui:1.4.3" (o tu versión)
// implementation 'androidx.lifecycle:lifecycle-runtime-ktx:2.6.1'
// implementation 'androidx.activity:activity-compose:1.7.2'
// implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1'
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.*
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BotonReceiverApp()
        }
    }
}

@Composable
fun BotonReceiverApp() {
    var estadoBoton by remember { mutableStateOf("Desconocido") }

    LaunchedEffect(Unit) {
        while (true) {
            estadoBoton = consultarEstadoBoton()
            delay(1000) // cada 1 segundo
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(text = "Estado del botón:")
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = estadoBoton,
                style = MaterialTheme.typography.headlineLarge
            )
        }
    }
}

suspend fun consultarEstadoBoton(): String = withContext(Dispatchers.IO) {
    try {
        val url = URL("http://192.168.4.1/estado_boton")
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"
        connection.connectTimeout = 3000
        connection.readTimeout = 3000

        val code = connection.responseCode
        if (code == 200) {
            val response = connection.inputStream.bufferedReader().readText().trim()
            return@withContext if (response == "1") "Presionado" else "No presionado"
        } else {
            return@withContext "Error HTTP $code"
        }
    } catch (e: Exception) {
        return@withContext "Error: ${e.message}"
    }
}






