package com.example.multiple_leds_controlled

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.multiple_leds_controlled.ui.theme.Multiple_leds_controlledTheme
import com.example.multiple_leds_controlled.views.LedControllers

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Multiple_leds_controlledTheme {
               setContent {
                   LedControllers()
               }
            }
        }
    }
}
