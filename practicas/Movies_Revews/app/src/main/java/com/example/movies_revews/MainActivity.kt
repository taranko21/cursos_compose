package com.example.movies_revews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.movies_revews.navigation.AppNavigation
import com.example.movies_revews.ui.theme.Movies_RevewsTheme
import com.example.movies_revews.views.LogScreen

class MainActivity : ComponentActivity() {
    private lateinit var dataStore: DataStore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        dataStore = DataStore(this)
        setContent {
            Movies_RevewsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    AppNavigation(dataStore)
                }
            }
        }
    }
}


