package com.example.base_datos_room

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.base_datos_room.ui.theme.Base_datos_roomTheme
import com.example.base_datos_room.views.HomeView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Base_datos_roomTheme {
                HomeView()
            }
        }
    }
}
