package com.example.read_sensor_data

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.read_sensor_data.ui.theme.Read_sensor_dataTheme
import com.example.read_sensor_data.views.HomeView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Read_sensor_dataTheme {
                setContent {
                    HomeView()
                }
            }
        }
    }
}

