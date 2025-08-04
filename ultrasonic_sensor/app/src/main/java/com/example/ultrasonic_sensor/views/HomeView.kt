package com.example.ultrasonic_sensor.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.net.HttpURLConnection
import java.net.URL


@Composable
fun HomeView() {
    var distance by remember { mutableIntStateOf(0) }
    var systemState by remember { mutableStateOf("State unknown") }
    var isAutoMode by remember { mutableStateOf(false) }
    val esp32Ip = "http://192.168.4.1"
    val client = OkHttpClient()

    fun sendCommand(path: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val request = Request.Builder()
                    .url("$esp32Ip$path")
                    .build()

                val response = client.newCall(request).execute()
                val result = response.body.string()

                withContext(Dispatchers.Main) {
                    when {
                        path.contains("ultrasonic_sensor") -> {
                            val value = result.filter { it.isDigit() || it == '.' }
                            if (value.isNotEmpty()) {
                                distance = value.toFloat().toInt()
                            }
                        }
                        path.contains("modo_auto") -> {
                            systemState = "Automatic"
                            isAutoMode = true
                        }
                        path.contains("modo_manual") -> {
                            systemState = "Manual"
                            isAutoMode = false
                        }
                        else -> {
                            systemState = result
                        }
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    systemState = "Error: ${e.message}"
                }
            }
        }
    }

    // Lógica para lectura automática solo cuando isAutoMode = true
    LaunchedEffect(isAutoMode) {
        while (isAutoMode) {
            val data = withContext(Dispatchers.IO) { sensorData() }
            val value = data.filter { it.isDigit() || it == '.' }
            if (value.isNotEmpty()) {
                distance = value.toFloat().toInt()
            }
            delay(1000L)
        }
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Scaffold { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
                Row(modifier = Modifier.width(500.dp), horizontalArrangement = Arrangement.Center) {
                    Text("Ultrasonic sensor data", fontSize = 30.sp, color = Color.Red)
                }
                Spacer(Modifier.height(90.dp))
                Row(modifier = Modifier.width(500.dp), horizontalArrangement = Arrangement.Center) {
                    Text("Sensor data: ", fontSize = 15.sp)
                    Spacer(Modifier.width(30.dp))
                    Text("$distance", fontSize = 20.sp)
                }

                Spacer(Modifier.height(90.dp))

                Row(modifier = Modifier.width(500.dp), horizontalArrangement = Arrangement.Center) {
                    Button(onClick = { sendCommand("/modo_manual/relay_on") },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Green)) {
                        Text("Activate", color = Color.Black)
                    }
                    Spacer(Modifier.width(10.dp))
                    Button(onClick = { sendCommand("/modo_manual/relay_off") },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red)) {
                        Text("Deactivate", color = Color.Black)
                    }
                    Spacer(Modifier.width(10.dp))
                    Button(onClick = { sendCommand("/ultrasonic_sensor") },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Yellow)) {
                        Text("Ultrasonic", color = Color.Black)
                    }
                }

                Spacer(Modifier.height(20.dp))

                Row(modifier = Modifier.width(500.dp), horizontalArrangement = Arrangement.Center) {
                    Button(onClick = { sendCommand("/modo_auto") }) {
                        Text("Automatic", color = Color.Black)
                    }
                    Spacer(Modifier.width(30.dp))
                    Button(onClick = { sendCommand("/modo_manual") }) {
                        Text("Manual", color = Color.Black)
                    }
                }

                Spacer(Modifier.height(30.dp))
                Row(modifier = Modifier.width(500.dp), horizontalArrangement = Arrangement.Center) {
                    Text("Current mode: $systemState", fontSize = 20.sp, color = Color.Blue)
                }
            }
        }
    }
}


suspend fun sensorData(): String = withContext(Dispatchers.IO) {
    try {
        val url = URL("http://192.168.4.1/ultrasonic_sensor")
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"
        connection.connectTimeout = 3000
        connection.readTimeout = 3000

        if (connection.responseCode == 200) {
            return@withContext connection.inputStream.bufferedReader().readText().trim()
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    ""
}
