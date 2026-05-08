package com.example.json_data_input.view

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.json_data_input.viewmodel.UserViewModel

@Composable
fun UserView(){
    val vm: UserViewModel = viewModel()

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
            Button(onClick = {
                vm.addUser()
            }) {
                Text("Add user")
            }
            Spacer(modifier = Modifier.height(15.dp))
            Text(vm.generateJson)
        }
    }
}