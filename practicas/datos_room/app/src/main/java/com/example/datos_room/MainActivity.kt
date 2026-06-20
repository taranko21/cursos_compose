package com.example.datos_room

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.datos_room.data.dataprovider.DatabaseProvider
import com.example.datos_room.ui.theme.Datos_roomTheme
import com.example.datos_room.vmodel.UserViewModel
import com.example.datos_room.vmodel.UserViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = DatabaseProvider.getDatabase((this))
        val viewmodel: UserViewModel by viewModels{
            UserViewModelFactory(db.userDao())
        }

        enableEdgeToEdge()
        setContent {
            Datos_roomTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues->
                    Users(
                        viewmodel = viewmodel,
                        paddingValues = paddingValues
                    )
                }
            }
        }
    }
}

@Composable
fun Users(viewmodel: UserViewModel, paddingValues: PaddingValues) {

    var text by remember { mutableStateOf("") }
    val users by viewmodel.users.collectAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        TextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Name") }

        )
        Button(
            onClick = {
                if (text.isNotBlank()) {
                    viewmodel.addUser(text)
                    text = ""
                }
            }
        )
        {
            Text("save")
        }
        users.forEach { user ->
            Text(text = user.name)
        }
    }
}