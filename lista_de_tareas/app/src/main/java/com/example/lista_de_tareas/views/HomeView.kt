package com.example.lista_de_tareas.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lista_de_tareas.dataclasses.Tareas
import com.example.lista_de_tareas.viewmodel.HomeViewModel

@Composable
fun HomeView(){
    val viewModel: HomeViewModel = viewModel()
    var text by remember { mutableStateOf("") }
    val ntareas by viewModel.tareas.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp, vertical = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = text,
            onValueChange = {text= it},
            label = { Text("Nueva tarea") }
        )
        Row {
            Button(
                onClick = {
                    viewModel.agregar(text)
                    text = ""

                }
            ) {
                Text("Creat Tarea")
            }
            Spacer(modifier = Modifier.width(15.dp))
            Button(
                onClick = {
                    viewModel.eliminar()
                }
            ) {
                Text("Eliminar tarea")
            }
        }
        Spacer(modifier = Modifier.height(30.dp))
        LazyColumn {
            items(ntareas, key = {it.id}){ tareas ->
                ItemRow(tareas,viewModel)
            }
        }
    }
}

@Composable
fun ItemRow(nuevastareas: Tareas, viewModel: HomeViewModel){
    Card(
        modifier = Modifier.
        fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier.padding(15.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){
            Checkbox(
               checked= nuevastareas.completada,
                onCheckedChange = {viewModel.cambiarEstado(
                    nuevastareas.id,
                    it
                )}
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = nuevastareas.tareas
            )
        }
    }
}

