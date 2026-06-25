package com.example.images_videos_name_change

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeView(){
    var selected by remember { mutableStateOf(FileType.IMAGES) }
    val viewModel = remember { HomeViewModel() }
    MaterialTheme {
        Scaffold{paddingValues ->
            Column(
                modifier = Modifier.fillMaxSize()
                    .padding(paddingValues)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFF87ACEA),
                                Color(0xFFACC2E7)
                            )
                        )
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text("Select the file type", fontSize = 30.sp, style = MaterialTheme.typography.headlineLarge)
                Spacer(modifier = Modifier.height(40.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    RadioButton(
                        selected = selected == FileType.IMAGES,
                        onClick = {selected = FileType.IMAGES}
                    )
                    Text("Images")
                    Spacer(modifier = Modifier.width(15.dp))
                    RadioButton(
                        selected = selected == FileType.VIDEOS,
                        onClick = {selected = FileType.VIDEOS}
                    )
                    Text("Videos")
                    Spacer(modifier = Modifier.width(15.dp))
                    RadioButton(
                        selected = selected == FileType.BOTH,
                        onClick = {selected = FileType.BOTH}
                    )
                    Text("Both")
                }
                Spacer(modifier = Modifier.height(40.dp))
                Text("Select the folder", fontSize = 30.sp, style = MaterialTheme.typography.headlineLarge)
                Spacer(modifier = Modifier.height(40.dp))
                Row (
                    horizontalArrangement = Arrangement.Center
                ){
                    Text(
                        text = if (viewModel.folderPath.isBlank()) {
                            "No folder selected"
                        } else {
                            "Folder to process:\n${viewModel.folderPath}"
                        }
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    Button(onClick = {
                        viewModel.selectFolder()
                    } ){
                        Text("Select")
                    }
                }
                Spacer(modifier = Modifier.height(40.dp))
                Button(
                    enabled = viewModel.folderPath.isNotBlank(),
                    onClick = {
                        viewModel.renameFiles(selected)
                    }
                ){
                    Text("Rename Files")
                }
                Spacer(modifier = Modifier.height(60.dp))
                if (viewModel.statusMessage.isNotBlank()) {
                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = viewModel.statusMessage
                    )
                }
            }
        }
    }
}