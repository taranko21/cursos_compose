package com.example.editor_de_archivos.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(
    navController: NavController
){
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
    ){
        Column(
            modifier = Modifier.padding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Editor de archivos", fontSize = 30.sp, fontWeight = FontWeight(800), color = Color(0xFFF5F5F5))
            Spacer(modifier = Modifier.height(100.dp))
            Text("Modos: ", fontSize = 20.sp, fontWeight = FontWeight(800), color = Color(0xFFF5F5F5))
            Spacer(modifier = Modifier.height(30.dp))
            Button(onClick = {
                navController.navigate("NameChangeView")
            }) {
                Text("Cambiar nombre")
            }
            Spacer(modifier = Modifier.height(30.dp))
            Button(onClick = {
                navController.navigate("FormatChange")
            }) {
                Text("Cambiar formato")
            }
        }
    }
}