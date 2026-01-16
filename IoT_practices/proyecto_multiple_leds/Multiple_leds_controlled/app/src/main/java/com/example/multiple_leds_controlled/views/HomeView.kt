package com.example.multiple_leds_controlled.views


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.multiple_leds_controlled.components.HomeViewComponents
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request

@Composable
fun LedControllers(){
    var status by remember { mutableStateOf("Estado: desconocido") }
   val esp32Ip = "http://192.168.4.1"
    val client = OkHttpClient()

    fun sendCommand(path: String){
        CoroutineScope(Dispatchers.IO).launch {
            try{
                val request = Request.Builder()
                    .url("$esp32Ip$path")
                    .build()

                val response = client.newCall(request).execute()
                val resultado = response.body?.string()?: "Error al obtener la respuesta"
                withContext (Dispatchers.Main){
                    status = "Estado: $resultado"
                }
            }catch (e: Exception){
                withContext(Dispatchers.Main){
                    status = "Error: ${e.message}"
                }
            }
        }
    }
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
            .padding(70.dp)
    ) {
        Spacer(modifier = Modifier.size(20.dp))
        HomeViewComponents(
            onClick = {path -> sendCommand(path)},
            text = "RED LED",
            contentDescription = "RED LED",
            color = Color.Red,
            ledPath = "/red_led"
        )
        Spacer(modifier = Modifier.size(10.dp))
        HomeViewComponents(
            onClick = {path -> sendCommand(path)},
            text = "GREEN LED",
            contentDescription = "GREEN LED",
            color = Color.Green,
            ledPath = "/green_led"
        )
        Spacer(modifier = Modifier.size(10.dp))
        HomeViewComponents(
            onClick = {path -> sendCommand(path)},
            text = "BLUE LED",
            contentDescription = "BLUE LED",
            color = Color.Blue,
            ledPath = "/blue_led"
        )
        Spacer(modifier = Modifier.size(10.dp))
        HomeViewComponents(
            onClick = {path -> sendCommand(path)},
            text = "WHITE LED",
            contentDescription = "WHITE LED",
            color = Color.Cyan,
            ledPath = "/white_led"
        )
        Spacer(modifier = Modifier.size(10.dp))
        HomeViewComponents(
            onClick = {path -> sendCommand(path)},
            text = "YELLOW LED",
            contentDescription = "YELLOW LED",
            color = Color.Yellow,
            ledPath = "/yellow_led"
        )
    }
}