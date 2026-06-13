package com.example.passwords.views

import android.content.ClipData
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.passwords.viewmodels.ThemeViewModel
import com.example.passwords.viewmodels.UserViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordView(){

    val dvm: ThemeViewModel = viewModel()
    val isDarkTheme by dvm.isDarkTheme.collectAsState(initial = false)

    val vm: UserViewModel = viewModel()
    val context = LocalContext.current

    val clipboardManager = LocalClipboard.current
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        vm.loadUser(context)
    }

    MaterialTheme(colorScheme = if (isDarkTheme) darkColorScheme() else lightColorScheme()) {
        Scaffold(
            modifier = Modifier.padding(),
            topBar = {
                TopAppBar(
                    title = {
                        Text("Password Generator")
                    },
                    navigationIcon = {
                        IconButton(onClick = {dvm.toggleTheme(isDarkTheme)}) {
                            Icon(imageVector = if (isDarkTheme) Icons.Default.DarkMode else Icons.Default.LightMode,
                                contentDescription = "Dark and Light Mode")
                        }
                    },
                    actions = {IconButton(onClick = {
                        vm.searchUser()
                        vm.foundUser?.let { user->
                            scope.launch {
                                clipboardManager.setClipEntry(
                                    ClipEntry(
                                        ClipData.newPlainText(
                                            "password",
                                            user.password
                                        )
                                    )
                                )
                            }
                        }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search username"
                        )
                    }}
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                TextField(
                    value = vm.website,
                    onValueChange = { vm.onWebsiteChange(it) },
                    label = { Text("Website") }
                )
                Spacer(modifier = Modifier.height(15.dp))
                TextField(
                    value = vm.email,
                    onValueChange = { vm.newEmailChange(it) },
                    label = { Text("Email/Username") }
                )
                Spacer(modifier = Modifier.height(15.dp))
                TextField(
                    value = vm.password,
                    onValueChange = { vm.newPasswordChange(it) },
                    label = { Text("Password") }
                )
                Spacer(modifier = Modifier.height(15.dp))
                Row {
                    Button(onClick = {vm.randomPassword()}) {
                        Text("Random")
                    }
                    Spacer(modifier = Modifier.width(15.dp))
                    Button(onClick = {vm.addUser(context)}) {
                        Text("Save")
                    }
                    Spacer(modifier = Modifier.width(15.dp))
                    Button(onClick = {vm.deleteUser(vm.website,context)}) {
                        Text("Delete")
                    }
                }
                Spacer(modifier = Modifier.height(15.dp))
                vm.foundUser?.let { user ->
                    Card(
                        modifier = Modifier.padding(16.dp)
                            .fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {

                            Text(text = "Email: ${user.email}")

                            Spacer(modifier = Modifier.height(10.dp))

                            Text(text = "Password: ${user.password}")

                        }
                    }
                }
                if (vm.message.isNotEmpty()){
                    Text(text = vm.message )
                }
            }
        }
    }
}