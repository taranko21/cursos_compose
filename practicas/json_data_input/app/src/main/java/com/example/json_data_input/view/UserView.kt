package com.example.json_data_input.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.json_data_input.viewmodel.UserViewModel

@Composable
fun UserView(){
    val vm: UserViewModel = viewModel()
    val context = LocalContext.current
    //Carga datos guardados al abrir la app
    LaunchedEffect(Unit) {
        vm.loadUsers(context)
    }
    Scaffold {paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            TextField(
                value = vm.name,
                onValueChange = {vm.onNameChange(it)},
                label = { Text("Name") }
            )
            Spacer(modifier = Modifier.height(15.dp))
            TextField(
                value = vm.age,
                onValueChange = {vm.onAgeChange(it)},
                label = {Text("Age")}
            )
            Spacer(modifier = Modifier.height(15.dp))
            Row {
                Button(onClick = {
                    vm.addUser(context)
                }) {
                    Text("Add user")
                }
                Spacer(modifier = Modifier.width(15.dp))
                Button(onClick = {
                    vm.searchUser()
                }) {
                    Text("Search")
                }
            }
            Spacer(modifier = Modifier.height(15.dp))
            vm.foundUser?.let { user ->
                    Text(text =  "name: ${user.name}")
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text =  "name: ${user.age} y.o")
            }
        }
    }
}