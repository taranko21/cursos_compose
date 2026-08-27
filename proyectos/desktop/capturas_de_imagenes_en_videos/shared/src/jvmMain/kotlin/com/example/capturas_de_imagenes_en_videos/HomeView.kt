package com.example.capturas_de_imagenes_en_videos

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.capturas_de_imagenes_en_videos.navigation.NavController
import com.example.capturas_de_imagenes_en_videos.navigation.Screen

@Composable
fun HomeView(navController: NavController) {
    MaterialTheme {
        Scaffold {paddingValues ->
            Column(
                modifier = Modifier.padding(paddingValues)
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFFFF512F),
                                Color(0xFFDD2476)
                            )
                        )
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("Modos: ", fontSize = 30.sp, fontWeight = FontWeight(100))
                Spacer(modifier = Modifier.height(50.dp))
                Button(onClick = {
                    navController.navigate(Screen.Automatic)
                }, modifier = Modifier
                    .width(200.dp)
                    .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF2D1B2E)
                    )
                ){
                    Text("Tiempo")
                }
                Spacer(modifier = Modifier.height(30.dp))
                Button(onClick = {
                    navController.navigate(Screen.Interval)
                }, modifier = Modifier
                    .width(200.dp)
                    .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF2D1B2E)
                    )){
                    Text("Intervalo")
                }
                Spacer(modifier = Modifier.height(30.dp))
                Button(onClick = {
                    navController.navigate(Screen.Time)
                }, modifier = Modifier
                    .width(200.dp)
                    .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF2D1B2E)
                    )){
                    Text("Aleatorio")
                }
            }
        }
    }
}