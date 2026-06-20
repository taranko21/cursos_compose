package com.example.datastore

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.datastore.ui.theme.DataStoreTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val darkModeStore = DarkModeStorafe(this)
            val darkMode = darkModeStore.getDarkMode.collectAsState(initial = true)
            DataStoreTheme (darkTheme = darkMode.value) {
                setContent{
                    DataStorageApp(darkModeStore, darkMode.value)
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DataStorageApp(darkModeStore: DarkModeStorafe, darkMode: Boolean) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val dataStore = StoreUserEmail(context)
    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
        ) {
            var email by rememberSaveable { mutableStateOf("") }
            val userEmail = dataStore.getEmail.collectAsState(initial = "")
            var darkMode by rememberSaveable { mutableStateOf(darkMode) }

            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Enter your name") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                scope.launch { dataStore.saveEmail(email) }
            }) {
                Text("Same email")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                scope.launch {
                    if (darkMode) {
                        darkModeStore.saveDarkMode(false)
                    } else {
                        darkModeStore.saveDarkMode(true)
                    }
                }
            }) {
                Text("Dark Mode")
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text(userEmail.value)
        }
    }
}

