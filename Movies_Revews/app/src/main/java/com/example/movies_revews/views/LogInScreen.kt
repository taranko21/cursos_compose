@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.movies_revews.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.movies_revews.DataStore
import com.example.movies_revews.ui.theme.MyTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun LogScreen(
    navController: NavHostController, dataStore: DataStore,isDarkTheme: Boolean) {
    var isDarkTheme by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        dataStore.isDarkTheme.collect { isDark ->
            isDarkTheme = isDark
        }
    }
    MyTheme(darkTheme = isDarkTheme) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Scaffold(
                topBar = {
                    CenterAlignedTopAppBar(
                        title = { Text(text = "Mrev") },
                        navigationIcon = {
                            IconButton(onClick = {
                                isDarkTheme= !isDarkTheme
                                CoroutineScope(Dispatchers.IO).launch {
                                    dataStore.saveTheme(isDarkTheme)
                                }
                            }) {
                                Icon(
                                    painter = painterResource(id = com.example.movies_revews.R.drawable.change_theme),
                                    contentDescription = "Back"
                                )
                            }
                        },
                    )
                },
                modifier = Modifier.fillMaxSize(),

                content = { paddingValues: PaddingValues ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        TextField(
                            value = "",
                            onValueChange = { /*TODO*/ },
                            label = { Text(text = "Email") },
                        )
                        Spacer(modifier = Modifier.height(30.dp))
                        TextField(
                            value = "",
                            onValueChange = { /*TODO*/ },
                            label = { Text(text = "Password") },
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        Button(onClick = {navController.navigate("movies")}) {
                            Text(text = "Log In")
                        }
                    }
                },
            )
        }
    }
}

