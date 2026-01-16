package com.example.wifi_esp32_connect

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request

class MainActivity : ComponentActivity() {

    private val esp32Ip = "http://192.168.4.1"
    private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                LEDController()
            }
        }
    }

    @Composable
    fun LEDController() {
        var status by remember { mutableStateOf("Estado: desconocido") }

        fun enviarComando(path: String) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val request = Request.Builder()
                        .url("$esp32Ip$path")
                        .build()

                    val response = client.newCall(request).execute()
                    val resultado = response.body?.string() ?: "Sin respuesta"
                    withContext(Dispatchers.Main) {
                        status = "Estado: $resultado"
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        status = "Error: ${e.message}"
                    }
                }
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize().padding(16.dp)
        ) {
            Text("Control de LED ESP32", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(20.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
                Button(onClick = { enviarComando("/led/on") }) {
                    Text("ENCENDER")
                }
                Button(onClick = { enviarComando("/led/off") }) {
                    Text("APAGAR")
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(status)
        }
    }
}