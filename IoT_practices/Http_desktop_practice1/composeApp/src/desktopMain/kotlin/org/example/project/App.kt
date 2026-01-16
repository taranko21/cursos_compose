package org.example.project


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.bodyAsText
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
@Preview
fun App() {
    MaterialTheme {
        val client = remember { HttpClient(CIO){
            install(ContentNegotiation){
                json()
                }
            }
        }
        var responseText by remember { mutableStateOf("Sin respuesta") }
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Button(onClick = {
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val response = client.get("http://192.168.4.1/led/on"){
                            parameter("state", "on")
                        }
                        responseText = response.bodyAsText()
                    }catch (e: Exception){
                        responseText = "Error: ${e.message}"
                    }
                }
            }) {
                Text("Encender Led")
            }
            Button(onClick = {
                CoroutineScope(Dispatchers.IO).launch {
                    try{
                        val response = client.get("http://192.168.4.1/led/off"){
                            parameter("state", "off")
                        }
                        responseText = response.bodyAsText()
                    }catch (e: Exception){
                        responseText = "Error: ${e.message}"
                    }
                }
            }) {
                Text("Apagar Led")
            }
            Text("Respuesta: $responseText", modifier = Modifier.padding(top = 10.dp))
        }
    }
}