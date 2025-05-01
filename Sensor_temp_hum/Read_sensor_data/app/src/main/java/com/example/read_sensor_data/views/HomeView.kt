package com.example.read_sensor_data.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

@Composable
fun HomeView() {

    var temperature: Int by remember { mutableIntStateOf(0) }
    var humidity: Int by remember { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            val data = sensorData()
            if (data.isNotEmpty()) {
                val lines = data.lines()
                lines.forEach { line ->
                    if ("Temperature" in line) {
                        val temp = line.filter { it.isDigit() || it == '.' }
                        temperature = temp.toFloat().toInt()
                    }
                    if ("Humidity" in line) {
                        val hum = line.filter { it.isDigit() || it == '.' }
                        humidity = hum.toFloat().toInt()
                    }
                }
            }

            delay(1000L) // Wait 3 seconds before next update
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Temperature:")
            Spacer(Modifier.height(16.dp))
            Text("$temperature °C", style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.height(32.dp))
            Text("Humidity:")
            Spacer(Modifier.height(16.dp))
            Text("$humidity %", style = MaterialTheme.typography.headlineMedium)
        }
    }
}

suspend fun sensorData(): String = withContext(Dispatchers.IO) {
    try {
        val url = URL("http://192.168.4.1/sensor")
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"
        connection.connectTimeout = 3000
        connection.readTimeout = 3000

        val code = connection.responseCode
        if (code == 200) {
            val response = connection.inputStream.bufferedReader().readText().trim()
            return@withContext response
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    ""
}
