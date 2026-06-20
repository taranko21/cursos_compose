package com.example.generador_de_contrasenas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
@Preview
fun App(){
    val viewModel = remember {
        UsersViewModel()
    }
    LaunchedEffect(Unit){
        viewModel.loadUser()
    }
    MaterialTheme {
        Scaffold(
            modifier = Modifier.padding(),
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text("Password Generator")
                    }
                )
            }
        ) {paddingValues ->
            Column(
                modifier = Modifier.fillMaxSize()
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Column {
                    Row {
                        TextField(
                            value = viewModel.website,
                            onValueChange = { viewModel.onWebsiteChange(it)},
                            label = { Text("Website") }
                        )
                        Spacer(modifier = Modifier.width(15.dp))
                        Button(onClick = {viewModel.searchUser()}) {
                            Text("Search")
                        }
                    }
                    Spacer(modifier = Modifier.height(15.dp))
                    TextField(
                        value = viewModel.email,
                        onValueChange = { viewModel.newEmailChange(it) },
                        label = { Text("Email/Username") }
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    Row {
                        TextField(
                            value = viewModel.password,
                            onValueChange = { viewModel.newPasswordChange(it) },
                            label = { Text("Password") }
                        )
                        Spacer(modifier = Modifier.width(15.dp))
                        Button(onClick = {viewModel.randomPassword()}){
                            Text("Random")
                        }
                    }
                    Spacer(modifier = Modifier.height(25.dp))
                    Row {
                        Button(onClick = {viewModel.addUser()}){
                            Text("Save")
                        }
                        Spacer(modifier = Modifier.width(15.dp))
                        Button(onClick = {viewModel.deleteUser(
                            viewModel.website
                                 )
                                }
                            )
                        {
                            Text("Delete")
                        }
                        Spacer(modifier = Modifier.width(15.dp))
                        Button(onClick = {viewModel.clearTextFields()}){
                            Text("Clear")
                        }
                    }
                    Spacer(modifier = Modifier.height(15.dp))
                    Text(viewModel.message)
                }

            }
        }
    }
}