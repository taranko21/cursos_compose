package com.example.json_users.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.json_users.viewmodel.UserViewModel

@Composable
fun HomeView(){
    val vm: UserViewModel = viewModel()
    val user = vm.user

    LaunchedEffect(Unit) {
        vm.loaduser()
    }
    Scaffold(modifier = Modifier.padding()) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (user!=null){
                Text("Name: ${user.name}")
                Text("Age: ${user.age}")
            }else{
                CircularProgressIndicator()
            }
        }
    }
}