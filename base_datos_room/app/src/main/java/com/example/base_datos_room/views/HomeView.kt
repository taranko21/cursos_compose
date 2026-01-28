package com.example.base_datos_room.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.base_datos_room.components.TextFieldComponent
import com.example.base_datos_room.inidatabase.IniDataBase
import com.example.base_datos_room.model.Users

@Composable
fun HomeView() {
    Scaffold {paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            var username by remember { mutableStateOf("") }
            var userage by remember { mutableStateOf("") }
            var useremail by remember { mutableStateOf("") }
            val database = IniDataBase().database
            val userDao = database.userDao()
            userDao.insertAll(Users(name = username, age = userage, email = useremail))
            TextFieldComponent(value = "name", onValueChange = { username = it }, label = "Enter your name")
            Spacer(modifier = Modifier.height(16.dp))
            TextFieldComponent(value = "age", onValueChange = { userage = it }, label = "Enter your age")
            Spacer(modifier = Modifier.height(16.dp))
            TextFieldComponent(value = "email", onValueChange = { useremail = it }, label = "Enter your email")
        }
    }
}