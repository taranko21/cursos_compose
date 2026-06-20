package com.example.darkthemedatastore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.darkthemedatastore.ui.theme.DarkThemeDataStoreTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DarkThemeDataStoreTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.primary
                ) {
                    DarkThemeSave()
                }
            }
        }
    }
}
@Composable
fun DarkThemeSave(){
    //Contexto y acceso al dataStore
    val context = LocalContext.current
    val dsDarkTheme = remember { DsDarkTheme(context) }

    //Obtener el estado del tema desde dataStore
    val isDarkTheme = dsDarkTheme.isDarkTheme.collectAsState(initial = false).value


    //Aplicar el tema de acuerdo con el estado
    val darkColors = darkColorScheme()
    val lightColor = lightColorScheme()

    val toggleTheme = remember { mutableStateOf(false) }

    LaunchedEffect(toggleTheme.value) {
        if(toggleTheme.value){
            dsDarkTheme.toogleTheme(isDarkTheme)
            toggleTheme.value = false
        }
    }

    //Cambiar el tema con un 'MatherialTHeme'
    MaterialTheme(
        colorScheme = if(isDarkTheme) darkColors else lightColor
    ){
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Current theme if ${if (isDarkTheme) "Dark" else "Light"}")
            Button(onClick = {
                toggleTheme.value = true
                }
            )
            {
                Text("Toggle theme")
            }
        }
    }


}